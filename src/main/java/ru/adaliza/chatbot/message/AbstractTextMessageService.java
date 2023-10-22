package ru.adaliza.chatbot.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWNV2;

public abstract class AbstractTextMessageService {

    protected SendMessage createTextReplyMessage(Long chatId, String text) {
        return createReplyKeyboardMessage(chatId, text, null);
    }

    protected SendMessage createReplyKeyboardMessage(Long chatId, String text, ReplyKeyboard keyboard) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        sendMessage.setReplyMarkup(keyboard);
        sendMessage.setParseMode(MARKDOWNV2);

        return sendMessage;
    }
}
