package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;

import java.io.Serializable;

@Service("addCommand")
@RequiredArgsConstructor
public class AddCommandService extends AbstractCommandService {
    public static final String FOR_ADDING = "Enter product name for adding";

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(ButtonData buttonData) {
        // TODO добавить ограничение на кол-во товаров
        return createKeyboardReplyMessage(buttonData, FOR_ADDING);
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineInnerMenuMarkup();
    }
}
