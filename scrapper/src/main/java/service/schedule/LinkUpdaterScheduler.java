package service.schedule;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import links.Parser;
import result.GitHubResultRecord;
import result.ParseResult;
import result.StackOverflowResultRecord;
import dto.LinkResponseDto;
import model.request.LinkUpdateRequest;
import model.response.GitHubRepositoryInfoResponse;
import model.response.StackOverflowQuestionInfoResponse;
import service.LinkService;
import service.LinkUpdater;
import service.client.BotClient;
import service.client.GitHubClient;
import service.client.StackOverflowClient;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler implements LinkUpdater {
    @Value("${supported-links}")
    private final String[] supportedLinks;
    private final LinkService linkService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;
    private final Parser parser;

    @Override
    @Scheduled(fixedDelayString = "#{@schedulingIntervalMillis}")
    public void update() {
        List<LinkResponseDto> allOldestLinksByLastUpdate = linkService.findAllOldestLinksByLastCheck();

        getUpdateForGitHubLinks(allOldestLinksByLastUpdate);
        getUpdateForStackOverflowLinks(allOldestLinksByLastUpdate);
    }

    private void getUpdateForGitHubLinks(List<LinkResponseDto> allOldestLinksByLastUpdate) {
        String gitHubLink = supportedLinks[0];
        allOldestLinksByLastUpdate.stream()
                .filter(it -> it.getUrl()
                        .toString()
                        .startsWith(gitHubLink))
                .forEach(it -> {
                    ParseResult parseResult = parser.checkLink(it.getUrl()
                            .toString());
                    GitHubRepositoryInfoResponse response = gitHubClient
                            .getGitHubRepositoryInfo((GitHubResultRecord) parseResult)
                            .block();
                    if (response != null && response.getUpdatedAt()
                            .isAfter(it.getLastUpdate())) {
                        linkService.setLastUpdate(it.getId(), response.getUpdatedAt());
                        ResponseEntity<Void> messageForBot = sendRequestToBot(it);

                        log.info("Get update for: id=" +
                                it.getId() +
                                "---" +
                                it.getUrl() +
                                "--- bot answer: " +
                                (messageForBot != null ? messageForBot.getStatusCode() : "null"));
                    } else {
                        linkService.setLastCheck(it.getId());
                    }
                });
    }

    private void getUpdateForStackOverflowLinks(List<LinkResponseDto> allOldestLinksByLastUpdate) {
        String stackOverFlowLink = supportedLinks[1];
        allOldestLinksByLastUpdate.stream()
                .filter(it -> it.getUrl()
                        .toString()
                        .startsWith(stackOverFlowLink))
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
                                        .map(StackOverflowQuestionInfoResponse.Items::getLastEditDate)
                                        .findFirst()
                                        .orElse(it.getLastUpdate());
                        if (responseLastUpdate.isAfter(it.getLastUpdate())) {
                            linkService.setLastUpdate(it.getId(), responseLastUpdate);
                            ResponseEntity<Void> messageForBot = sendRequestToBot(it);
                            log.info("Get update for: id=" +
                                    it.getId() +
                                    " --- " +
                                    it.getUrl() +
                                    " --- bot answer: " +
                                    (messageForBot != null ? messageForBot.getStatusCode() : "null"));
                        } else {
                            linkService.setLastCheck(it.getId());
                        }
                    }
                });
    }

    @Nullable
    private ResponseEntity<Void> sendRequestToBot(LinkResponseDto it) {
        return botClient.postLinks(LinkUpdateRequest.builder()
                        .tgChat(it.getId())
                        .description("Update available")
                        .url(it.getUrl()
                                .toString())
                        .build())
                .block();
    }
}
