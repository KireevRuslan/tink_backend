package ap.backend;

import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;

public class linkparserTest {

    @Test
    public void GitHubValidTest() throws URISyntaxException {
        String URL = "https://github.com/arsiharsi/tink-repo";
        BackendLinkParser linkParser = new BackendLinkParser(URL);
        Assert.assertEquals("LinkData[data=Name: arsiharsi Repo: tink-repo, links=MonoFlatMap]",linkParser.getData().toString());
    }
    @Test
    public void StackOverflowValidTest() throws URISyntaxException {
        String URL = "https://stackoverflow.com/questions/47179937/how-to-get-string-from-monostring-in-reactive-java";
        BackendLinkParser linkParser = new BackendLinkParser(URL);
        Assert.assertEquals("LinkData[data=47179937, links=MonoFlatMap]",linkParser.getData().toString());
    }
    @Test
    public void InvalidTest() throws URISyntaxException {
        String URL = "invalid";
        BackendLinkParser linkParser = new BackendLinkParser(URL);
        Assert.assertEquals("LinkData[data=null, links=null]",linkParser.getData().toString());
    }
}