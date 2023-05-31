package model.dto.updates;


import lombok.*;
import model.dto.UpdatesDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StackOverflowUpdatesDto  implements UpdatesDto {
    private Long id;
    private boolean isAnswered;
    private Integer answerCount;
}