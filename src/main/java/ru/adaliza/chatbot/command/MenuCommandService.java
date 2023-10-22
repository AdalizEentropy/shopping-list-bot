package ru.adaliza.chatbot.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;

import java.io.Serializable;

@Service("menuCommand")
public class MenuCommandService extends AbstractCommandService {

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(ButtonData buttonData) {
        return createKeyboardReplyMessage(buttonData, "Select command");
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineMainMenuMarkup();
    }
}
