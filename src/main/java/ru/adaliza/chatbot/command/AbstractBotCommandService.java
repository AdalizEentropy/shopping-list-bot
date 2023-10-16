package ru.adaliza.chatbot.command;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWNV2;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adaliza.chatbot.button.Buttons;

public abstract class AbstractBotCommandService implements BotCommandService {

    protected SendMessage createTextReplyMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        sendMessage.setReplyMarkup(Buttons.inlineInnerMenuMarkup());
        sendMessage.setParseMode(MARKDOWNV2);

        return sendMessage;
    }

    protected SendMessage createButtonReplyMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        sendMessage.setReplyMarkup(Buttons.inlineProductsMarkup());

        return sendMessage;
    }
}
