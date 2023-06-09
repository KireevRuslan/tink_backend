package model.request;

import lombok.*;

import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LinkUpdateRequest {

    private Long tgChat;

    private String url;

    private String description;

    private Map<String, String> changes;
}
