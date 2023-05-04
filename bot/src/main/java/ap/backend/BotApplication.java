package ap.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import configuration.ApplicationConfig;
import service.bot.BotStarter;

@SpringBootApplication(scanBasePackages = {"bot.*"})
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        System.out.println(config);
        BotStarter botStarter = ctx.getBean(BotStarter.class);
        botStarter.start();
    }

}
