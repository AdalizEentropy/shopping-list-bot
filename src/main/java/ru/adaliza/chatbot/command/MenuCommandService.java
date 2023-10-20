package ru.adaliza.chatbot.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import ru.adaliza.chatbot.message.AbstractMessageService;

@Service("menuCommand")
public class MenuCommandService extends AbstractMessageService implements BotCommandService {

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        return createMainMenuKeyboardReplyMessage(chatId);
    }
}
