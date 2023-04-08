package app;

import Dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/links")
public class ScrapperController {
    @GetMapping
    ScrapperRequest listLinks(@RequestHeader int id){
        return new ScrapperRequest(new ScrapperResponse[1], 12);
    }
    @PostMapping
    LinkRecordDto linkAdd(@RequestHeader int id) throws URISyntaxException {
        return new LinkRecordDto(new URI("add"));
    }
    @DeleteMapping
    LinkDeleteDto linkDelete(@RequestHeader int id) throws URISyntaxException {
        return new LinkDeleteDto(new URI("delete"));
    }
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public NullDto handleException(MethodArgumentNotValidException e){
        return new NullDto("Некорректные параметры запроса",
                e.getStatusCode().toString(),
                e.getObjectName(),
                e.getLocalizedMessage(),
                new String[]{String.valueOf(e.getStackTrace())});
    }
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public NullDto urlException(MethodArgumentNotValidException e){
        return new NullDto("Ссылка не найдена",
                e.getStatusCode().toString(),
                e.getObjectName(),
                e.getLocalizedMessage(),
                new String[]{String.valueOf(e.getStackTrace())});
    }

}













