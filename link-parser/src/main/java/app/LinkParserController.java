package app;

import ap.backend.BackendLinkParser;
import org.springframework.web.bind.annotation.*;
@RestController
public class LinkParserController {
    @GetMapping("/")
    @ResponseBody
    String hello() {
        return "<html>Go to http://localhost:8080/url:?url=URL with correct URL so it starts working</html>";
    }

    @GetMapping("/url:")
    @ResponseBody
    public String getUrl(@RequestParam(required = false) String url) {
        BackendLinkParser linkParser = new BackendLinkParser(url);
        return "<html><p>URL: " + url + "</p><p>Data:"+linkParser.getData()+"</p></html>";
    }

}
