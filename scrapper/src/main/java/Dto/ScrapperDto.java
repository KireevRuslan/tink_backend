package Dto;

import lombok.Data;

import java.util.List;

@Data
public class ScrapperDto {
    private Long id;
    private String code;
    private String description;
    private String exceptionName;
    private String exceptionMessage;
    private List<Long> stacktrace;


}




