package service;

import model.response.ListTgChatResponse;
import model.response.TgChatResponse;

public interface TgChatService {
    TgChatResponse registerChat(Long tgChatId);

    TgChatResponse removeChat(Long tgChatId);

    ListTgChatResponse findAll();
}