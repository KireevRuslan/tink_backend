package configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import mapper.ChatMapper;
import mapper.LinkMapper;
import repository.jpa.JpaGitHubUpdatesRepository;
import repository.jpa.JpaLinkRepository;
import repository.jpa.JpaStackOverflowUpdatesRepository;
import repository.jpa.JpaTgChatRepository;
import service.LinkService;
import service.TgChatService;
import service.jpa.JpaLinkService;
import service.jpa.JpaTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {

    @Bean
    public LinkService linkService(JpaLinkRepository jpaLinkRepository,
                                   JpaTgChatRepository jpaTgChatRepository,
                                   LinkMapper linkMapper,
                                   JpaGitHubUpdatesRepository gitHubUpdatesRepository,
                                   JpaStackOverflowUpdatesRepository stackOverflowUpdatesRepository) {
        return new JpaLinkService(jpaLinkRepository,
                jpaTgChatRepository,
                linkMapper,
                gitHubUpdatesRepository,
                stackOverflowUpdatesRepository);
    }

    @Bean
    public TgChatService tgChatService(JpaTgChatRepository chatRepository,
                                       ChatMapper chatMapper) {
        return new JpaTgChatService(chatRepository, chatMapper);
    }
}