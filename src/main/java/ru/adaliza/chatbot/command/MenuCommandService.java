package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.language.LanguageConverter;

import java.io.Serializable;

@Service("menuCommand")
@RequiredArgsConstructor
public class MenuCommandService extends AbstractCommandService {
    private final LanguageConverter languageConverter;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        return createKeyboardReplyMessage(
                updateContext,
                languageConverter.getLanguageData(updateContext.user()).selectCommand());
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineMainMenuMarkup();
    }
}
