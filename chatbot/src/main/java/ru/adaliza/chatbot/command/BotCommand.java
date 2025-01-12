package ru.adaliza.chatbot.command;

import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum BotCommand {
    START("/start"),
    ADD("/add"),
    CLEAR("/clear"),
    SHOW("/show"),
    REMOVE("/remove"),
    MENU("/menu"),
    HELP("/help"),
    SETTINGS("/settings"),
    CATEGORY("/use_category"),
    ENABLE("/enable"),
    DISABLE("/disable"),
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
}
