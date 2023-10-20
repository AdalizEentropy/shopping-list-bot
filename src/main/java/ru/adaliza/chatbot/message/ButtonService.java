package ru.adaliza.chatbot.message;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import ru.adaliza.chatbot.command.BotCommand;
import ru.adaliza.chatbot.command.BotCommandService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ButtonService implements MessageService {
    private final Map<String, BotCommandService> commands;

    @Override
    public SendMessage replyOnMessage(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String command = update.getCallbackQuery().getData();
        BotCommand botCommand = BotCommand.valueOfCommand(command);

        SendMessage replyMessage;
        switch (botCommand) {
            case ADD -> replyMessage = commands.get("addCommand").createMessageForCommand(chatId);
            case CLEAR -> replyMessage =
                    commands.get("clearCommand").createMessageForCommand(chatId);
            case REMOVE -> replyMessage =
                    commands.get("removeCommand").createMessageForCommand(chatId);
            case SHOW -> replyMessage = commands.get("showCommand").createMessageForCommand(chatId);
            case HELP -> replyMessage = commands.get("helpCommand").createMessageForCommand(chatId);
            case MENU -> replyMessage = commands.get("menuCommand").createMessageForCommand(chatId);
            default -> replyMessage =
                    commands.get("unknownCommand").createMessageForCommand(chatId);
        }

        return replyMessage;
    }
}
