package mapper;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import domain.entity.Link;
import model.dto.LinkResponseDto;
import model.request.AddLinkRequest;
import model.response.LinkResponse;

import java.net.URI;
import java.util.List;

@Mapper(componentModel = "spring", imports = {URI.class})
public interface LinkMapper {
    @Mapping(target = "url", expression = "java(addLinkRequest.getLink().toString())")
    Link addLinkRequestToLink(AddLinkRequest addLinkRequest);

    @Mapping(target = "url", expression = "java(URI.create(link.getUrl()))")
    LinkResponse linkToLinkResponse(Link link);

    @Mapping(target = "url", expression = "java(URI.create(link.getUrl()))")
    LinkResponseDto linkToLinkResponseDto(Link link);

    List<LinkResponse> linkListToLinkResponseList(List<Link> links);

    List<LinkResponseDto> linkListToLinkResponseDtoList(List<Link> links);
}