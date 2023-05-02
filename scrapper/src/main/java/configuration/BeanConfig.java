package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import links.Parser;
import links.GitHubLinkParse;
import links.LinkParse;
import links.StackOverflowLinkParse;

@Configuration
@EnableScheduling
public class BeanConfig {
    @Bean
    public long schedulingIntervalMillis(ApplicationConfig config) {
        return config.scheduler()
                .interval()
                .toMillis();
    }

    @Bean
    public Parser linkParser() {
        Parser parser = new Parser();
        parser.setLinks(LinkParse.link(new GitHubLinkParse(),
                new StackOverflowLinkParse()));
        return parser;
    }
}
