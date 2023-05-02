package configuration.access;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import configuration.ApplicationConfig;
import service.SendMessageService;
import service.rabbit.ScrapperQueueProducer;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class RabbitQueueAccessConfig {
    @Bean
    public SendMessageService sendMessageService(ApplicationConfig config, RabbitTemplate rabbitTemplate) {
        return new ScrapperQueueProducer(config, rabbitTemplate);
    }
}