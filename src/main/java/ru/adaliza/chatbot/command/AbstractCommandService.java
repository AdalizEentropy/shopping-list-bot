package ru.adaliza.chatbot.command;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.HTML;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public abstract class AbstractCommandService implements BotCommandService {

    protected abstract InlineKeyboardMarkup getReplyMarkup();

    protected EditMessageText createKeyboardReplyMessage(UpdateContext updateContext, String text) {
        var chatIdStr = String.valueOf(updateContext.chatId());
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatIdStr);
        editMessage.setMessageId(updateContext.messageId());
        editMessage.setText(text);
        editMessage.setParseMode(HTML);
        editMessage.setReplyMarkup(getReplyMarkup());

        return editMessage;
    }
}
