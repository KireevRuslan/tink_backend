package configuration;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import TgBotClass.TgBot;

@Configuration
public class TgBotConfig {
    @Bean("tg_bot_starter")
    public TgBot startBot(TgBot tgBot){
        return tgBot;
    }
}