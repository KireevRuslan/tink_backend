package service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.springframework.stereotype.Service;
import configuration.TelegramConfig;
import service.CommandList;
import service.Command;
import service.UnknownCommand;

import java.util.List;

@Service
public class BotStarter implements Bot {
    private final TelegramBot bot;
    private final CommandList commandList;

    public BotStarter(TelegramConfig telegramConfig, CommandList commandList) {
        this.bot = new TelegramBot(telegramConfig.token());
        this.commandList = commandList;
        botCommands();
    }

    private void botCommands() {
        List<Command> commands = commandList.getCommandList();
        BotCommand[] botCommands = commands.stream()
                .map(Command::toApiCommand)
                .toArray(BotCommand[]::new);
        SetMyCommands request = new SetMyCommands(botCommands);
        bot.execute(request);
    }

    public int process(List<Update> updates) {
        updates.forEach(update -> {
            Message message = update.message();
            if (message != null) {
                SendMessage sendMessage = handleByCommand(update, message);
                bot.execute(sendMessage);
            }
        });
        return 0;
    }

    public SendMessage handleByCommand(Update update, Message message) {
        Command command = commandList.getCommandList()
                .stream()
                .filter(it -> it.command()
                        .equals(message.text()))
                .findFirst()
                .orElseGet(UnknownCommand::new);
        return command.handle(update);
    }

    public void start() {
        bot.setUpdatesListener(updates -> {
            process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public void close() {
        bot.removeGetUpdatesListener();
    }
}