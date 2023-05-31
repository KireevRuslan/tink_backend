package service;

import model.request.LinkUpdateRequest;

public interface SendMessageService {
    void sendMessage(LinkUpdateRequest requestBody);
}