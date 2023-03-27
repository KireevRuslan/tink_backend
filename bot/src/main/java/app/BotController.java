package app;
import Dto.BotDto;
import Dto.BotResponse;
import Dto.NullDto;
import io.swagger.annotations.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/updates")
public class BotController {



    @PostMapping(consumes = "application/json", produces = "application/json")
    public BotDto update(@Valid @RequestBody BotDto dataClass) {
        return new BotDto(dataClass.getId(), dataClass.getUrl(), dataClass.getDescription(), dataClass.getTgChatIds());
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public NullDto handleException(MethodArgumentNotValidException e){
        return new NullDto("Некорректные параметры запроса", e.getStatusCode().toString(), e.getObjectName(), e.getLocalizedMessage(), new String[]{String.valueOf(e.getStackTrace())});
    }

}








