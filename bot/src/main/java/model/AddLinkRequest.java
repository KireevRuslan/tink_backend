package model;

import lombok.*;

import java.net.URI;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddLinkRequest {
    private URI link;
}