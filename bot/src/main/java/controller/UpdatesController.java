package controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import model.request.LinkUpdateRequest;
import service.UpdateService;
import service.update.ScrapperUpdates;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UpdatesController implements Updates {
    private final ScrapperUpdates updateService;

    @Override
    public ResponseEntity<Void> postUpdates(
            @Parameter(
                    in = ParameterIn.DEFAULT,
                    required = true,
                    schema = @Schema())
            @Valid
            @RequestBody LinkUpdateRequest body) {
        updateService.receiver(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}