package ru.adaliza.chatbot.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.model.Product;

import java.util.List;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWNV2;

public abstract class AbstractMessageService {
    public static final String ERROR_STATE = "Phase error";

    protected SendMessage createMainMenuKeyboardReplyMessage(Long chatId) {
        return createKeyboardReplyMessage(chatId, "Select command", Buttons.inlineMainMenuMarkup());
    }

    protected SendMessage createTextWithKeyboardReplyMessage(Long chatId, String text) {
        return createKeyboardReplyMessage(chatId, text, Buttons.inlineInnerMenuMarkup());
    }

    protected SendMessage createProductsKeyboardReplyMessage(
            Long chatId, String text, List<Product> products) {
        return createKeyboardReplyMessage(chatId, text, Buttons.inlineProductsMarkup(products));
    }

    protected SendMessage createKeyboardReplyMessage(Long chatId, String text, ReplyKeyboard keyboard) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        sendMessage.setReplyMarkup(keyboard);
        sendMessage.setParseMode(MARKDOWNV2);

        return sendMessage;
    }
}
