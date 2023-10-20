package ru.adaliza.chatbot.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import ru.adaliza.chatbot.message.AbstractMessageService;

@Service("unknownCommand")
public class UnknownCommandService extends AbstractMessageService implements BotCommandService {

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        var text = "Unknown command\\!";
        return createTextWithKeyboardReplyMessage(chatId, text);
    }
}
