package ap.backend.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import links.GitHubLinkParse;
import links.LinkParse;
import links.Parser;
import links.StackOverflowLinkParse;
import result.GitHubResultRecord;
import result.StackOverflowResultRecord;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
        LinkParse supportedLinkParse = LinkParse.link(
                new GitHubLinkParse(),
                new StackOverflowLinkParse());
        parser.setLinks(supportedLinkParse);
    }
    @ParameterizedTest
    @ValueSource(strings = {
            "https://github.com/",
            "https://stackoverflow.com/",
            "https://stackoverflow.com/users",
            "https://stackoverflow.com/questions/",
            "https://github.com/pulls",
            "https://www.google.com/",
            "google.com",
            "www.google.com/"
    })
    void checkLink_shouldReturnNull(String input) {
        assertNull(parser.checkLink(input));
    }

    @Test
    void checkLink_shouldReturnGitHubResultRecord() {
        String input = "https://github.com/KireevRuslan/TinkKontest";
        String expected = new GitHubResultRecord("RuslanKireev", "TinkKontest").getResult();

        assertEquals(expected, parser.checkLink(input)
                .getResult());
    }

    @Test
    void checkLink_shouldReturnGStackOverflowResultRecord() {
        String input = "https://stackoverflow.com/questions/75988522/webpack-cli-failed-to-load-webpack-config-ts-config-typeerror-err-unknown";
        String expected = new StackOverflowResultRecord("75988522").getResult();

        assertEquals(expected, parser.checkLink(input)
                .getResult());
    }
}


