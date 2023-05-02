package service.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import configuration.ApplicationConfig;
import model.request.LinkUpdateRequest;
import service.SendMessageService;

//@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer implements SendMessageService {
    private final ApplicationConfig config;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(LinkUpdateRequest update) {
        rabbitTemplate.convertAndSend(config.queryName(), update);
    }
}