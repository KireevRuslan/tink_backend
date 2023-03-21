package app;
import org.springframework.web.bind.annotation.*;
@RestController
public class BotController {
    @RequestMapping("/")
    String hello() {
        return "<html>Bot</html>";
    }
}
