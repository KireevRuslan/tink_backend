package service.schedule;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import links.Parser;
import result.GitHubResultRecord;
import result.ParseResult;
import result.StackOverflowResultRecord;
import model.dto.LinkResponseDto;
import model.dto.updates.GitHubUpdatesDto;
import model.dto.updates.StackOverflowUpdatesDto;
import model.request.LinkUpdateRequest;
import model.response.GitHubRepositoryInfoResponse;
import model.response.StackOverflowQuestionInfoResponse;
import service.LinkService;
import service.SendMessageService;
import service.client.GitHubClient;
import service.client.StackOverflowClient;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final LinkService linkService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final SendMessageService sendMessageService;
    private final Parser parser;

    @Scheduled(fixedDelayString = "#{@schedulingIntervalMillis}")
    public void update() {
        List<LinkResponseDto> allOldestLinksByLastUpdate = linkService.findAllOldestLinksByLastCheck();

        getUpdatesForGitHubLinks(allOldestLinksByLastUpdate);
        getUpdatesForStackOverflowLinks(allOldestLinksByLastUpdate);
    }

    private void getUpdatesForGitHubLinks(List<LinkResponseDto> allOldestLinksByLastUpdate) {
        allOldestLinksByLastUpdate
                .stream()
                .filter(it -> it.getType()
                        .equals("github"))
                .forEach(it -> {
                    ParseResult parseResult = parser.checkLink(it.getUrl()
                            .toString());
                    GitHubRepositoryInfoResponse response = gitHubClient
                            .getGitHubRepositoryInfo((GitHubResultRecord) parseResult)
                            .block();
                    if (response != null && response.getUpdatedAt()
                            .isAfter(it.getLastUpdate())) {
                        GitHubUpdatesDto updates = (GitHubUpdatesDto) linkService
                                .findUpdatesByLinkIdAndLinkType(it.getId(), it.getType());
                        Map<String, String> gitHubChanges = getGitHubChanges(updates, response);
                        linkService.setGitHubLastUpdate(it.getId(), response);
                        sendRequestToBot(it, gitHubChanges);

                        log.info("Get update for: id=" + it.getId() + " --- " + it.getUrl() + " --- " + gitHubChanges);
                    } else {
                        linkService.setLastCheck(it.getId());
                    }
                });
    }

    private void getUpdatesForStackOverflowLinks(List<LinkResponseDto> allOldestLinksByLastUpdate) {
        allOldestLinksByLastUpdate
                .stream()
                .filter(it -> it.getType()
                        .equals("stack"))
                .forEach(it -> {
                    ParseResult parseResult = parser.checkLink(it.getUrl()
                            .toString());
                    StackOverflowQuestionInfoResponse response = stackOverflowClient
                            .getStackOverflowQuestionInfo((StackOverflowResultRecord) parseResult)
                            .block();
                    if (response != null) {
                        OffsetDateTime responseLastUpdate =
                                response.getItems()
                                        .stream()
                                        .map(StackOverflowQuestionInfoResponse.Items::getLastActivityDate)
                                        .findFirst()
                                        .orElse(it.getLastUpdate());
                        if (responseLastUpdate.isAfter(it.getLastUpdate())) {
                            StackOverflowUpdatesDto updates = (StackOverflowUpdatesDto) linkService
                                    .findUpdatesByLinkIdAndLinkType(it.getId(), it.getType());
                            Map<String, String> stackChanges = getStackOverflowChanges(updates, response);
                            linkService.setStackOverflowLastUpdate(it.getId(), response);
                            sendRequestToBot(it, stackChanges);
                            log.info("Get update for: id=" + it.getId() + " --- " + it.getUrl() + " --- " + stackChanges);
                        } else {
                            linkService.setLastCheck(it.getId());
                        }
                    }
                });
    }

    private void sendRequestToBot(LinkResponseDto it, Map<String, String> changes) {
        sendMessageService.sendMessage(LinkUpdateRequest.builder()
                .tgChat(it.getId())
                .description("Update available")
                .url(it.getUrl()
                        .toString())
                .changes(changes)
                .build());
    }

    private Map<String, String> getGitHubChanges(GitHubUpdatesDto updates,
                                                 GitHubRepositoryInfoResponse response) {
        Map<String, String> changes = new HashMap<>();
        if (response.getWatchers() > updates.getWatchers()) {
            changes.put("watchers", "+" + (response.getWatchers() - updates.getWatchers()));
        }
        if (response.getForksCount() > updates.getForksCount()) {
            changes.put("fork_count", "+" + (response.getForksCount() - updates.getForksCount()));
        }
        return changes;
    }

    private Map<String, String> getStackOverflowChanges(StackOverflowUpdatesDto updates,
                                                        StackOverflowQuestionInfoResponse response) {
        Integer answerCount = response.getItems()
                .stream()
                .map(StackOverflowQuestionInfoResponse.Items::getAnswerCount)
                .findFirst()
                .orElse(-1);

        Boolean isAnswered = response.getItems()
                .stream()
                .map(StackOverflowQuestionInfoResponse.Items::isAnswered)
                .findFirst()
                .orElse(false);

        Map<String, String> changes = new HashMap<>();
        if (answerCount > updates.getAnswerCount()) {
            changes.put("answer_count", "+" + (answerCount - updates.getAnswerCount()));
        }
        if (isAnswered != updates.isAnswered()) {
            changes.put("is_answered", isAnswered.toString());
        }
        return changes;
    }
}