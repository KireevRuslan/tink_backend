package mapper;

import org.mapstruct.Mapper;
import domain.entity.Chat;
import model.response.TgChatResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    TgChatResponse chatToTgChatResponse(Chat chat);

    List<TgChatResponse> chatListToTgChatResponseList(List<Chat> chatList);
}