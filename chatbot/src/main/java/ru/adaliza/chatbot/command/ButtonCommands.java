package ru.adaliza.chatbot.command;

import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ButtonCommands {
    private final Map<BotCommand, BotCommandService> commands;

    public ButtonCommands(Map<String, BotCommandService> commands) {
        this.commands =
                Map.ofEntries(
                        Map.entry(BotCommand.ADD, commands.get("addCommand")),
                        Map.entry(BotCommand.CLEAR, commands.get("clearCommand")),
                        Map.entry(BotCommand.REMOVE, commands.get("removeCommand")),
                        Map.entry(BotCommand.SHOW, commands.get("showCommand")),
                        Map.entry(BotCommand.HELP, commands.get("helpCommand")),
                        Map.entry(BotCommand.SETTINGS, commands.get("settingsCommand")),
                        Map.entry(BotCommand.CATEGORY, commands.get("useCategoryCommand")),
                        Map.entry(BotCommand.MENU, commands.get("menuCommand")),
                        Map.entry(BotCommand.UNKNOWN, commands.get("unknownCommand")));
    }

    public BotCommandService getCommand(BotCommand command) {
        return Optional.ofNullable(commands.get(command)).orElse(commands.get(BotCommand.UNKNOWN));
    }
}
