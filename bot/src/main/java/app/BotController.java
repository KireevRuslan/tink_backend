package app;
import Dto.BotResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bot/process", method = RequestMethod.GET)
public class BotController {
    @GetMapping
    public BotResponse process(@RequestParam(name = "message", required = false, defaultValue = "default message") String message) {
        return new BotResponse("Stubbed response for " + message);
    }
}