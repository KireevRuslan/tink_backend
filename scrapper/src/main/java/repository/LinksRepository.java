package repository;

import dto.LinkResponseDto;
import model.request.AddLinkRequest;
import model.request.RemoveLinkRequest;
import model.response.LinkResponse;
import model.response.ListLinksResponse;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinksRepository {
    LinkResponse add(Long tgChatId, AddLinkRequest request);

    LinkResponse remove(Long tgChatId, RemoveLinkRequest request);

    ListLinksResponse findAll(Long tgChatId);

    List<LinkResponseDto> findOneOldestLinksByLastCheckForEachUser();

    void setLastCheck(Long id);

    void setLastUpdate(Long id, OffsetDateTime update);

    Boolean chatIsExists(Long tgChatId);
}