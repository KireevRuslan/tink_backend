package Dto;

import lombok.Data;

import java.util.List;

@Data
public class BotDto {
    private Long id;
    private String url;
    private String description;
    private List<Long> tgChatIds;
}




