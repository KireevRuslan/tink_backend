package service.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import exception.BadRequestException;
import model.request.AddLinkRequest;
import model.request.RemoveLinkRequest;
import model.response.LinkResponse;
import model.response.ListLinksResponse;

import static org.jooq.impl.DSL.select;
import static domain.jooq.link_info.tables.Chat.CHAT;
import static domain.jooq.link_info.tables.Link.LINK;

@Service
@RequiredArgsConstructor
public class JooqLinkService {
    // String query = "INSERT INTO link_info.link(url, chat_id) " +
//                "SELECT ?,? " +
//                "WHERE NOT EXISTS(" +
//                "SELECT url FROM link_info.link WHERE chat_id=? AND url=?)";
    private final DSLContext context;

    @Transactional
    public LinkResponse addLink(Long tgChatId, AddLinkRequest request) {
        boolean isExists = context.fetchExists(select().from(CHAT)
                .where(CHAT.CHAT_ID.eq(tgChatId)));
        if (!isExists) {
            throw new BadRequestException("Чат с id=" + tgChatId + " не существует");
        }
        Record1<Long> returningResult = context.insertInto(LINK, LINK.TYPE, LINK.URL, LINK.CHAT_ID)
                .values(request.getType(), request.getLink()
                        .toString(), tgChatId)
                .returningResult(LINK.ID)
                .fetchOne();
        return LinkResponse.builder()
                .url(request.getLink())
                .id(returningResult.value1())
                .build();
    }

    public LinkResponse removeLink(Long tgChatId, RemoveLinkRequest request) {
        return null;

    }

    public ListLinksResponse findAllLinksByTgChatId(Long tgChatId) {
        return null;
    }
}