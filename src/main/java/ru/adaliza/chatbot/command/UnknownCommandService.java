package ru.adaliza.chatbot.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;

import java.io.Serializable;

@Service("unknownCommand")
public class UnknownCommandService extends AbstractCommandService {

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        var text = "Unknown command!";
        return createKeyboardReplyMessage(updateContext, text);
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineInnerMenuMarkup();
    }
}
