package ru.adaliza.chatbot.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CommandService implements BotCommandService {

    @Override
    public SendMessage replyOnCommand(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String command = update.getCallbackQuery().getData();
        BotCommand botCommand = BotCommand.valueOfCommand(command);

        SendMessage replyMessage;
        switch (botCommand) {
            case ADD -> replyMessage = addCommand(chatId);
            case EDIT -> replyMessage = editCommand(chatId);
            case REMOVE -> replyMessage = removeCommand(chatId);
            case SHOW -> replyMessage = showCommand(chatId);
            case HELP -> replyMessage = helpCommand(chatId);
            default -> replyMessage = unknownCommand(chatId);
        }

        return replyMessage;
    }

    private SendMessage addCommand(Long chatId) {
        var text = "Enter product name for adding.";
        return createTextReplyMessage(chatId, text);
    }

    private SendMessage editCommand(Long chatId) {
        var text = "Enter product for edit.";
        return createTextReplyMessage(chatId, text);
    }

    private SendMessage removeCommand(Long chatId) {
        var text = "Enter product for remove.";
        return createTextReplyMessage(chatId, text);
    }

    private SendMessage showCommand(Long chatId) {
        var text = "Your shopping list:";
        return createTextReplyMessage(chatId, text);
    }

    private SendMessage helpCommand(Long chatId) {
        var text = "Here will be help";
        return createTextReplyMessage(chatId, text);
    }

    private SendMessage unknownCommand(Long chatId) {
        var text = "Unknown command!";
        return createTextReplyMessage(chatId, text);
    }

    private SendMessage createTextReplyMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        sendMessage.setReplyMarkup(Buttons.inlineInnerMenuMarkup());

        return sendMessage;
    }
}
