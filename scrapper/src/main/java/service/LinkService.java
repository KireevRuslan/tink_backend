package service;

import model.dto.LinkResponseDto;
import model.dto.UpdatesDto;
import model.request.AddLinkRequest;
import model.request.RemoveLinkRequest;
import model.response.GitHubRepositoryInfoResponse;
import model.response.LinkResponse;
import model.response.ListLinksResponse;
import model.response.StackOverflowQuestionInfoResponse;

import java.util.List;

public interface LinkService {
    LinkResponse addLink(Long tgChatId, AddLinkRequest request);

    LinkResponse removeLink(Long tgChatId, RemoveLinkRequest request);

    ListLinksResponse findAllLinksByTgChatId(Long tgChatId);

    List<LinkResponseDto> findAllOldestLinksByLastCheck();

    UpdatesDto findUpdatesByLinkIdAndLinkType(Long linkId, String type);

    void setLastCheck(Long id);


    void setStackOverflowLastUpdate(Long id, StackOverflowQuestionInfoResponse response);

    void setGitHubLastUpdate(Long id, GitHubRepositoryInfoResponse update);
}