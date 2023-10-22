package ru.adaliza.chatbot.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;

import java.io.Serializable;

@Service("helpCommand")
public class HelpCommandService extends AbstractCommandService {

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(ButtonData buttonData) {
        var text = "Here will be help";
        return createKeyboardReplyMessage(buttonData, text);
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineInnerMenuMarkup();
    }
}
