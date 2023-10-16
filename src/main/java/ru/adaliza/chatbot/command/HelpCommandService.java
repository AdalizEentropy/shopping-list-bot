package ru.adaliza.chatbot.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Qualifier("helpCommand")
public class HelpCommandService extends AbstractBotCommandService {

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        var text = "Here will be help";
        return createTextReplyMessage(chatId, text);
    }
}
