package ru.adaliza.chatbot.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adaliza.chatbot.button.Buttons;

@Service
@Qualifier("addCommand")
public class AddCommandService extends AbstractBotCommandService {
    public static final String FOR_ADDING = "Enter product name for adding";

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        return createTextReplyMessage(chatId, FOR_ADDING);
    }

    @Override
    protected SendMessage createTextReplyMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        sendMessage.setReplyMarkup(Buttons.forceReplyInnerMenuMarkup());

        return sendMessage;
    }
}
