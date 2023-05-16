package service.bot;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import io.micrometer.core.instrument.Counter;
import java.util.List;
import org.springframework.stereotype.Service;
import configuration.TelegramConfig;
import service.commands.Command;
import service.commands.CommandList;
import service.commands.impl.UnknownCommand;

@Service
public class BotStarter implements Bot {
    private final TelegramBot bot;
    private final CommandList commandList;
    private final Counter counter;

    public BotStarter(TelegramConfig telegramConfig, CommandList commandList, Counter counter) {
        this.bot = new TelegramBot("5810088925:AAHRFFT26JpExA27t927RMNh7zGdYwtImk0");
        this.commandList = commandList;
        this.counter = counter;
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
                counter.increment();
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
