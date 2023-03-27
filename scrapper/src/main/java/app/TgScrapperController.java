package app;

import Dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/tg-chat")
public class TgScrapperController {


    @PostMapping("/{id}")
    String tgAdd(@PathVariable int id){
        return "Chat Id: " + id;
    }
    @DeleteMapping("/{id}")
    String tgDelete(@PathVariable int id){
        return "Chat Id: " + id;
    }
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public TgNullDto handleException(MethodArgumentNotValidException e){
        return new TgNullDto("Некорректные параметры запроса",
                e.getStatusCode().toString(),
                e.getObjectName(),
                e.getLocalizedMessage(),
                new String[]{String.valueOf(e.getStackTrace())});
    }
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public TgNullDto tgException(MethodArgumentNotValidException e){
        return new TgNullDto("Ссылка не найдена",
                e.getStatusCode().toString(),
                e.getObjectName(),
                e.getLocalizedMessage(),
                new String[]{String.valueOf(e.getStackTrace())});
    }
}
