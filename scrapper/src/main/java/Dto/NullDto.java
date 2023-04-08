package Dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated

public record NullDto(String description, String code, String exceptionName, String exceptionMessage, String[] stacktrace) {

}
