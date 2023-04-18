package service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dto.LinkResponseDto;
import exception.BadRequestException;
import exception.DataAlreadyExistException;
import exception.DataNotFoundException;
import model.request.AddLinkRequest;
import model.request.RemoveLinkRequest;
import model.response.LinkResponse;
import model.response.ListLinksResponse;
import repository.LinksRepository;
import service.LinkService;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinksService implements LinkService {
    private final LinksRepository linksRepository;

    @Override
    @Transactional
    public LinkResponse addLink(Long tgChatId, AddLinkRequest request) {
        if (!linksRepository.chatIsExists(tgChatId)) {
            throw new BadRequestException("Чат с id=" + tgChatId + " не существует");
        }
        LinkResponse response = linksRepository.add(tgChatId, request);
        if (response.getId() < 0) {
            throw new DataAlreadyExistException(
                    "Ссылка: " + response.getUrl() + " уже существует у пользователя с id=" + tgChatId);
        }
        return response;
    }

    @Override
    @Transactional
    public LinkResponse removeLink(Long tgChatId, RemoveLinkRequest request) {
        if (!linksRepository.chatIsExists(tgChatId)) {
            throw new BadRequestException("Чат с id=" + tgChatId + " не существует");
        }
        LinkResponse response = linksRepository.remove(tgChatId, request);
        if (response.getId() < 0) {
            throw new DataNotFoundException(
                    "Ссылка: " + response.getUrl() + " не существует у пользователя с id=" + tgChatId);
        }
        return response;
    }

    @Override
    @Transactional
    public List<LinkResponseDto> findAllOldestLinksByLastCheck() {
        return linksRepository.findOneOldestLinksByLastCheckForEachUser();

    }

    @Override
    @Transactional
    public void setLastCheck(Long id) {
        linksRepository.setLastCheck(id);
    }

    @Override
    public void setLastUpdate(Long id, OffsetDateTime update) {
        linksRepository.setLastUpdate(id, update);
    }

    @Override
    @Transactional
    public ListLinksResponse findAllLinksByTgChatId(Long tgChatId) {
        if (!linksRepository.chatIsExists(tgChatId)) {
            throw new BadRequestException("Чат с id=" + tgChatId + " не существует");
        }
        return linksRepository.findAll(tgChatId);
    }
}