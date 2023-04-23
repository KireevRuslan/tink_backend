package service;

import dto.LinkResponseDto;
import model.request.AddLinkRequest;
import model.request.RemoveLinkRequest;
import model.response.LinkResponse;
import model.response.ListLinksResponse;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {
    LinkResponse addLink(Long tgChatId, AddLinkRequest request);

    LinkResponse removeLink(Long tgChatId, RemoveLinkRequest request);

    ListLinksResponse findAllLinksByTgChatId(Long tgChatId);

    List<LinkResponseDto> findAllOldestLinksByLastCheck();

    void setLastCheck(Long id);

    void setLastUpdate(Long id, OffsetDateTime update);
}