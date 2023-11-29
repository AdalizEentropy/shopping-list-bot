package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.InlineKeyboardInitializer;
import ru.adaliza.chatbot.language.LanguageConverter;
import ru.adaliza.chatbot.language.LanguageData;

import java.io.Serializable;

@Service("menuCommand")
@RequiredArgsConstructor
public class MenuCommandService extends AbstractCommandService {
    private final LanguageConverter languageConverter;
    private final InlineKeyboardInitializer keyboardInitializer;
    private LanguageData languageData;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        languageData = languageConverter.getLanguageData(updateContext.user());
        return createKeyboardReplyMessage(updateContext, languageData.selectCommand());
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return keyboardInitializer.inlineMainMenuMarkup(languageData);
    }
}
