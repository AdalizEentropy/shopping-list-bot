package ru.adaliza.chatbot.command;

import java.util.stream.Stream;

public enum BotCommand {
    START("/start"),
    ADD("/add"),
    EDIT("/edit"),
    SHOW("/show"),
    REMOVE("/remove"),
    HELP("/help");

    private final String command;

    BotCommand(String command) {
        this.command = command;
    }

    public static BotCommand valueOfCommand(String value) {
        return Stream.of(BotCommand.values())
                .filter(v -> v.command.equals(value))
                .findFirst()
                .orElse(null);
    }

    public String getTextCommand() {
        return command;
    }
}
