package service.jpa;



import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import domain.entity.Chat;
import domain.entity.GitHubUpdates;
import domain.entity.Link;
import domain.entity.StackOverflowUpdates;
import exception.BadRequestException;
import exception.DataAlreadyExistException;
import exception.DataNotFoundException;
import mapper.LinkMapper;
import model.dto.LinkResponseDto;
import model.dto.UpdatesDto;
import model.dto.updates.GitHubUpdatesDto;
import model.dto.updates.StackOverflowUpdatesDto;
import model.request.AddLinkRequest;
import model.request.RemoveLinkRequest;
import model.response.GitHubRepositoryInfoResponse;
import model.response.LinkResponse;
import model.response.ListLinksResponse;
import model.response.StackOverflowQuestionInfoResponse;
import repository.jpa.JpaGitHubUpdatesRepository;
import repository.jpa.JpaLinkRepository;
import repository.jpa.JpaStackOverflowUpdatesRepository;
import repository.jpa.JpaTgChatRepository;
import service.LinkService;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository linkRepository;
    private final JpaTgChatRepository chatRepository;
    private final LinkMapper linkMapper;

    private final JpaGitHubUpdatesRepository gitHubUpdatesRepository;

    private final JpaStackOverflowUpdatesRepository stackOverflowUpdatesRepository;

    @Override
    @Transactional
    public LinkResponse addLink(Long tgChatId, AddLinkRequest request) {
        Chat foundChat = returnChatIfExists(tgChatId);
        foundChat.getLinks()
                .stream()
                .map(Link::getUrl)
                .filter(it -> it.equals(request.getLink()
                        .toString()))
                .findFirst()
                .ifPresent(it -> {
                    throw new DataAlreadyExistException(
                            "Ссылка: " + request.getLink()
                                    .toString() + " уже существует у пользователя с id=" +
                                    tgChatId);
                });
        Link savedLink = linkRepository.save(Link.builder()
                .url(request.getLink()
                        .toString())
                .type(request.getType())
                .chat(foundChat)
                .build());
        addUpdatesByLinkType(request, savedLink.getId());
        return linkMapper.linkToLinkResponse(savedLink);
    }

    @Override
    @Transactional
    public LinkResponse removeLink(Long tgChatId, RemoveLinkRequest request) {
        Chat foundChat = returnChatIfExists(tgChatId);
        Link foundLink = foundChat.getLinks()
                .stream()
                .filter(it -> it.getUrl()
                        .equals(request.getLink()
                                .toString()))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException(
                        "Ссылка: " + request.getLink() + " не существует у пользователя с id=" + tgChatId));

        linkRepository.deleteById(foundLink.getId());
        return linkMapper.linkToLinkResponse(foundLink);
    }

    @Override
    public ListLinksResponse findAllLinksByTgChatId(Long tgChatId) {
        Chat foundChat = returnChatIfExists(tgChatId);
        List<Link> foundLinksByChatId = linkRepository.findAllByChat(foundChat);
        ListLinksResponse response = new ListLinksResponse();
        response.setLinks(linkMapper.linkListToLinkResponseList(foundLinksByChatId));
        return response;
    }

    @Override
    public List<LinkResponseDto> findAllOldestLinksByLastCheck() {
        List<Link> foundLinks = linkRepository.findOneOldestLinkByLastCheckForEachUser();
        return linkMapper.linkListToLinkResponseDtoList(foundLinks);
    }

    @Override
    public UpdatesDto findUpdatesByLinkIdAndLinkType(Long linkId, String type) {
        switch (type) {
            case "github" -> {
                GitHubUpdates gitHubUpdates =
                        gitHubUpdatesRepository.findById(linkId)
                                .orElseThrow(() -> new DataNotFoundException(
                                        "Обновлений для ссылки с id=" + linkId + " не найдены"));
                return GitHubUpdatesDto.builder()
                        .id(gitHubUpdates.getId())
                        .forksCount(gitHubUpdates.getForksCount())
                        .watchers(gitHubUpdates.getWatchers())
                        .build();
            }
            case "stack" -> {
                StackOverflowUpdates stackOverflowUpdates =
                        stackOverflowUpdatesRepository.findById(linkId)
                                .orElseThrow(() -> new DataNotFoundException(
                                        "Обновлений для ссылки с id=" + linkId + " не найдены"));

                return StackOverflowUpdatesDto.builder()
                        .id(stackOverflowUpdates.getId())
                        .answerCount(stackOverflowUpdates.getAnswerCount())
                        .isAnswered(stackOverflowUpdates.isAnswered())
                        .build();
            }
            default -> throw new DataNotFoundException("Updates for link with id=" + linkId + " not found");
        }
    }

    @Override
    @Transactional
    public void setLastCheck(Long id) {
        Link foundLink = linkRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Ссылка с id=" + id + " не найдена"));
        foundLink.setLastCheck(OffsetDateTime.now());

    }

    @Override
    @Transactional
    public void setStackOverflowLastUpdate(Long id, StackOverflowQuestionInfoResponse response) {
        OffsetDateTime lastUpdate = response.getItems()
                .stream()
                .map(StackOverflowQuestionInfoResponse.Items::getLastActivityDate)
                .findFirst()
                .orElse(null);
        Link foundLink = linkRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Ссылка с id=" + id + " не найдена"));
        foundLink.setLastCheck(OffsetDateTime.now());
        foundLink.setLastUpdate(lastUpdate);

        Boolean isAnswered = response.getItems()
                .stream()
                .map(StackOverflowQuestionInfoResponse.Items::isAnswered)
                .findFirst()
                .orElse(null);
        Integer answerCount = response.getItems()
                .stream()
                .map(StackOverflowQuestionInfoResponse.Items::getAnswerCount)
                .findFirst()
                .orElse(null);

        StackOverflowUpdates stackOverflowUpdates =
                stackOverflowUpdatesRepository.findById(id)
                        .orElseThrow(() ->
                                new DataNotFoundException(
                                        "Обновления для ссылки с id=" + id + " не найдены"));
        stackOverflowUpdates.setAnswered(Boolean.TRUE.equals(isAnswered));
        stackOverflowUpdates.setAnswerCount(answerCount);
    }

    @Override
    @Transactional
    public void setGitHubLastUpdate(Long id, GitHubRepositoryInfoResponse response) {
//        String query = "UPDATE link_info.link " +
//                "SET last_check = ?, last_update=? " +
//                "WHERE id = ?";
//        jdbcTemplate.update(query, OffsetDateTime.now(), update, id);

        Link foundLink = linkRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Ссылка с id=" + id + " не найдена"));
        foundLink.setLastCheck(OffsetDateTime.now());
        foundLink.setLastUpdate(response.getUpdatedAt());

        GitHubUpdates gitHubUpdates =
                gitHubUpdatesRepository.findById(id)
                        .orElseThrow(() ->
                                new DataNotFoundException(
                                        "Обновления для ссылки с id=" + id + " не найдены"));
        gitHubUpdates.setForksCount(response.getForksCount());
        gitHubUpdates.setWatchers(response.getWatchers());

//        String query = "UPDATE link_info.github_updates " +
//                "SET forks_count = ?, watchers=? " +
//                "WHERE id = ?";
//        jdbcTemplate.update(query, response.getForksCount(), response.getWatchers(), id);
    }

    private void addUpdatesByLinkType(AddLinkRequest request, Long linkId) {
        switch (request.getType()) {
            case "github" -> gitHubUpdatesRepository.save(GitHubUpdates.builder()
                    .id(linkId)
                    .build());
            case "stack" -> stackOverflowUpdatesRepository.save(StackOverflowUpdates.builder()
                    .id(linkId)
                    .build());
            default -> throw new DataNotFoundException("Updates for link with id=" + linkId + " not found");
        }
    }

    private Chat returnChatIfExists(Long tgChatId) {
        return chatRepository.findById(tgChatId)
                .orElseThrow(() -> new BadRequestException("Чат с id=" + tgChatId + " не найден"));
    }
}