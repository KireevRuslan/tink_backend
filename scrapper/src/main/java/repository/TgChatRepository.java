package repository;

import model.response.ListTgChatResponse;
import model.response.TgChatResponse;

public interface TgChatRepository {
    TgChatResponse add(Long tgChatId);

    TgChatResponse remove(Long tgChatId);

    ListTgChatResponse findAll();
}