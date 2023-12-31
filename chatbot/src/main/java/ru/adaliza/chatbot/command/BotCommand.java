package ru.adaliza.chatbot.command;

import java.util.stream.Stream;

public enum BotCommand {
    START("/start"),
    ADD("/add"),
    CLEAR("/clear"),
    SHOW("/show"),
    REMOVE("/remove"),
    MENU("/menu"),
    HELP("/help"),
    UNKNOWN("/unknown");

    private final String command;

    BotCommand(String command) {
        this.command = command;
    }

    public static BotCommand valueOfCommand(String value) {
        return Stream.of(BotCommand.values())
                .filter(v -> v.command.equals(value))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public String getCommand() {
        return command;
    }
}
