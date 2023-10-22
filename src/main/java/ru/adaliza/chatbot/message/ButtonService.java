package ru.adaliza.chatbot.message;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import ru.adaliza.chatbot.command.BotCommand;
import ru.adaliza.chatbot.command.BotCommandService;
import ru.adaliza.chatbot.command.ButtonData;
import ru.adaliza.chatbot.model.User;
import ru.adaliza.chatbot.service.UserService;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ButtonService implements MessageService<Serializable> {
    private final Map<String, BotCommandService> commands;
    private final UserService userService;

    @Override
    public BotApiMethod<Serializable> replyOnMessage(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String command = update.getCallbackQuery().getData();
        BotCommand botCommand = BotCommand.valueOfCommand(command);
        ButtonData buttonData = new ButtonData(chatId, messageId, command);

        if (botCommand == BotCommand.UNKNOWN) {
            return executeMayBeProductButton(buttonData);
        } else {
            userService.updatePhase(chatId, botCommand);
        }

        BotApiMethod<Serializable> replyMessage;
        switch (botCommand) {
            case ADD -> replyMessage =
                    commands.get("addCommand").createMessageForCommand(buttonData);
            case CLEAR -> replyMessage =
                    commands.get("clearCommand").createMessageForCommand(buttonData);
            case REMOVE -> replyMessage =
                    commands.get("removeCommand").createMessageForCommand(buttonData);
            case SHOW -> replyMessage =
                    commands.get("showCommand").createMessageForCommand(buttonData);
            case HELP -> replyMessage =
                    commands.get("helpCommand").createMessageForCommand(buttonData);
            case MENU -> replyMessage =
                    commands.get("menuCommand").createMessageForCommand(buttonData);
            default -> replyMessage =
                    commands.get("unknownCommand").createMessageForCommand(buttonData);
        }

        return replyMessage;
    }

    private BotApiMethod<Serializable> executeMayBeProductButton(ButtonData buttonData) {
        Optional<User> user = userService.getUser(buttonData.chatId());
        if (user.isPresent() && user.get().getChatPhase() == BotCommand.REMOVE) {
            return commands.get("removeCommand").createMessageForCommand(buttonData);
        } else {
            return commands.get("unknownCommand").createMessageForCommand(buttonData);
        }
    }
}
