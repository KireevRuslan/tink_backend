package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public TelegramConfig telegramConfig(ApplicationConfig config) {
        return config.bot();
    }
}
