package controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import exception.BadRequestException;
import model.request.AddLinkRequest;
import model.request.RemoveLinkRequest;
import model.response.LinkResponse;
import model.response.ListLinksResponse;
import service.jdbc.JdbcLinksService;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class LinksController implements Links {
    private final HttpServletRequest request;
    private final JdbcLinksService jdbcLinksService;

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(
            @Parameter(
                    in = ParameterIn.HEADER,
                    required = true, schema = @Schema())
            @RequestHeader(
                    value = "Tg-Chat-Id")
            Long tgChatId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            ListLinksResponse links = jdbcLinksService.findAllLinksByTgChatId(tgChatId);
            return new ResponseEntity<>(links, HttpStatus.OK);
        }
        throw new BadRequestException("Некоректный запрос");
    }

    @Override
    public ResponseEntity<LinkResponse> postLinks(
            @Parameter(
                    in = ParameterIn.HEADER,
                    required = true,
                    schema = @Schema())
            @RequestHeader(
                    value = "Tg-Chat-Id")
            Long tgChatId,
            @Parameter(
                    in = ParameterIn.DEFAULT,
                    required = true,
                    schema = @Schema())
            @Valid
            @RequestBody
            AddLinkRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            LinkResponse response = jdbcLinksService.addLink(tgChatId, body);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        throw new BadRequestException("Некоректный запрос");
    }

    @Override
    public ResponseEntity<LinkResponse> deleteLinks(
            @Parameter(
                    in = ParameterIn.HEADER,
                    required = true,
                    schema = @Schema())
            @RequestHeader(
                    value = "Tg-Chat-Id")
            Long tgChatId,
            @Parameter(
                    in = ParameterIn.DEFAULT,
                    required = true,
                    schema = @Schema())
            @Valid
            @RequestBody
            RemoveLinkRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            LinkResponse response = jdbcLinksService.removeLink(tgChatId, body);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        throw new BadRequestException("Некоректный запрос");
    }
}

