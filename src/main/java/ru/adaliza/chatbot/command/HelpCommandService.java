package ru.adaliza.chatbot.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import ru.adaliza.chatbot.message.AbstractMessageService;

@Service("helpCommand")
public class HelpCommandService extends AbstractMessageService implements BotCommandService {

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        var text = "Here will be help";
        return createTextWithKeyboardReplyMessage(chatId, text);
    }
}
