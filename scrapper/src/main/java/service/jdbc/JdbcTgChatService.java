package service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import exception.DataAlreadyExistException;
import exception.DataNotFoundException;
import model.response.ListTgChatResponse;
import model.response.TgChatResponse;
import repository.TgChatRepository;
import service.TgChatService;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final TgChatRepository tgChatRepository;

    @Override
    @Transactional
    public TgChatResponse registerChat(Long tgChatId) {
        TgChatResponse response = tgChatRepository.add(tgChatId);
        if (response.getTgChatId() < 0) {
            throw new DataAlreadyExistException("Чат с id=" + tgChatId + " уже существует");
        }
        return response;
    }

    @Override
    @Transactional
    public TgChatResponse removeChat(Long tgChatId) {
        TgChatResponse response = tgChatRepository.remove(tgChatId);
        if (response.getTgChatId() < 0) {
            throw new DataNotFoundException("Чат с id=" + tgChatId + " не найден");
        }
        return response;
    }

    @Override
    public ListTgChatResponse findAll() {
        return tgChatRepository.findAll();
    }
}