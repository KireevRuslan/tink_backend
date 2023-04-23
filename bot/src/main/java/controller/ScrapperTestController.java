package controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import model.request.AddLinkRequest;
import model.request.RemoveLinkRequest;
import model.response.LinkResponse;
import model.response.ListLinksResponse;
import service.client.ScrapperClient;

@RestController
@RequestMapping("/scrapper")
@RequiredArgsConstructor
public class ScrapperTestController {

    private final ScrapperClient scrapperClient;

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader Long thChatId) {
        return scrapperClient.getLinks(thChatId)
                .block();
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> postLinks(@RequestHeader Long thChatId, @RequestBody AddLinkRequest addLinkRequest) {
        return scrapperClient.postLinks(thChatId, addLinkRequest)
                .block();
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> deleteLinks(@RequestHeader Long thChatId, @RequestBody RemoveLinkRequest removeLinkRequest) {
        return scrapperClient.deleteLinks(thChatId, removeLinkRequest)
                .block();
    }

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable(value = "id") Long thChatId) {
        return scrapperClient.registerChat(thChatId)
                .block();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable(value = "id") Long thChatId) {
        return scrapperClient.deleteChat(thChatId)
                .block();
    }
}