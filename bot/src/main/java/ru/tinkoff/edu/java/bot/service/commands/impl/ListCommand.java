package ru.tinkoff.edu.java.bot.service.commands.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.service.LinkService;
import ru.tinkoff.edu.java.bot.service.commands.Command;

import java.util.List;

@Service
public class ListCommand implements Command {
    @Getter
    private static final String LINK_LIST_IS_EMPTY = "Список отслеживаемых ссылок пустой";

    private final LinkService linkService;

    public ListCommand(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "показать отслеживаемые ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        String answer =LINK_LIST_IS_EMPTY;
        if (linkService.getLinkList() != null && !linkService.getLinkList()
                .isEmpty()) {
            List<String> linkList = linkService.getLinkList();
            answer = String.join("\n", linkList);
        }
        SendMessage sendMessage = new SendMessage(update.message()
                .chat()
                .id(), answer);
        sendMessage.parseMode(ParseMode.HTML);
        return sendMessage;
    }
}
