package ru.adaliza.chatbot.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Qualifier("unknownCommand")
public class UnknownCommandService extends AbstractBotCommandService {

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        var text = "Unknown command\\!";
        return createTextReplyMessage(chatId, text);
    }
}
