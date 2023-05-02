package model.dto.updates;

import lombok.*;
import model.dto.UpdatesDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GitHubUpdatesDto implements UpdatesDto {
    private Long id;
    private Integer forksCount;
    private Integer watchers;
}