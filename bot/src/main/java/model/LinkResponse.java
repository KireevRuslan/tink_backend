package model;

import lombok.*;

import java.net.URI;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkResponse {
    private Long id;
    private URI url;
}