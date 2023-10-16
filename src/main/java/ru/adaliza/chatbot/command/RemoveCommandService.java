package ru.adaliza.chatbot.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Qualifier("removeCommand")
public class RemoveCommandService extends AbstractBotCommandService {

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        var text = "Choose product for remove";
        return createButtonReplyMessage(chatId, text);
    }
}
