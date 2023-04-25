package configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.jdbc.JdbcLinkRepository;
import repository.jdbc.JdbcLinkUpdatesRepository;
import repository.jdbc.JdbcTgChatRepository;
import service.LinkService;
import service.TgChatService;
import service.jdbc.JdbcLinksService;
import service.jdbc.JdbcTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {

    @Bean
    public LinkService linkService(JdbcLinkRepository jdbcLinkRepository,
                                   JdbcLinkUpdatesRepository jdbcLinkUpdatesRepository) {
        return new JdbcLinksService(jdbcLinkRepository, jdbcLinkUpdatesRepository);
    }

    @Bean
    public TgChatService tgChatService(JdbcTgChatRepository tgChatRepository) {
        return new JdbcTgChatService(tgChatRepository);
    }
}