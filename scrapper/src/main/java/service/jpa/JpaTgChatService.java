package service.jpa;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import domain.entity.Chat;
import exception.DataAlreadyExistException;
import exception.DataNotFoundException;
import mapper.ChatMapper;
import model.response.ListTgChatResponse;
import model.response.TgChatResponse;
import repository.jpa.JpaTgChatRepository;
import service.TgChatService;

import java.util.List;

@RequiredArgsConstructor
public class JpaTgChatService implements TgChatService {
    private final JpaTgChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Override
    @Transactional
    public TgChatResponse registerChat(Long tgChatId) {
        chatRepository.findById(tgChatId)
                .ifPresent(it -> {
                    throw new DataAlreadyExistException("Чат с id=" + tgChatId + " уже существует");
                });
        Chat savedChat = chatRepository.save(Chat.builder()
                .tgChatId(tgChatId)
                .build());
        return chatMapper.chatToTgChatResponse(savedChat);
    }

    @Override
    @Transactional
    public TgChatResponse removeChat(Long tgChatId) {
        chatRepository.findById(tgChatId)
                .orElseThrow(() -> new DataNotFoundException("Чат с id=" + tgChatId + " не найден"));
        chatRepository.deleteById(tgChatId);
        return TgChatResponse.builder()
                .tgChatId(tgChatId)
                .build();
    }

    @Override
    public ListTgChatResponse findAll() {
        List<Chat> chats = chatRepository.findAll();
        ListTgChatResponse response = new ListTgChatResponse();
        response.setTgChatList(chatMapper.chatListToTgChatResponseList(chats));
        return response;
    }
}