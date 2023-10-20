package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import ru.adaliza.chatbot.message.AbstractMessageService;
import ru.adaliza.chatbot.service.UserService;

@Service("addCommand")
@RequiredArgsConstructor
public class AddCommandService extends AbstractMessageService implements BotCommandService {
    public static final String FOR_ADDING = "Enter product name for adding";
    private final UserService userService;

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        boolean updated = userService.updatePhase(chatId, BotCommand.ADD);
        if (updated) {
            return createTextWithKeyboardReplyMessage(chatId, FOR_ADDING);
        } else {
            return createTextWithKeyboardReplyMessage(chatId, ERROR_STATE);
        }
    }
}
