package ru.adaliza.chatbot.command;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWNV2;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public abstract class AbstractCommandService implements BotCommandService {

    protected abstract InlineKeyboardMarkup getReplyMarkup();

    protected EditMessageText createKeyboardReplyMessage(ButtonData buttonData, String text) {
        var chatIdStr = String.valueOf(buttonData.chatId());
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatIdStr);
        editMessage.setMessageId(buttonData.messageId());
        editMessage.setText(text);
        editMessage.setParseMode(MARKDOWNV2);
        editMessage.setReplyMarkup(getReplyMarkup());

        return editMessage;
    }
}
