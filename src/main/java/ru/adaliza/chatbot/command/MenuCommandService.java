package ru.adaliza.chatbot.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adaliza.chatbot.button.Buttons;

@Service
@Qualifier("menuCommand")
public class MenuCommandService extends AbstractBotCommandService {
    
    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, "Choose command");
        sendMessage.setReplyMarkup(Buttons.inlineMainMenuMarkup());

        return sendMessage;
    }
}
