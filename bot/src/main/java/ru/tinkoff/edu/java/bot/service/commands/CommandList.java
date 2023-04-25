package ru.tinkoff.edu.java.bot.service.commands;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.commands.impl.*;

import java.util.List;

@Component
public class CommandList {
    private final List<Command> commandList;

    public CommandList(StartCommand start, HelpCommand help, TrackCommand track,
                       UntrackCommand untrack, ListCommand listCommand) {
        this.commandList = List.of(start, help, track, untrack, listCommand);
    }

    public List<Command> getCommandList() {
        return commandList;
    }
}
