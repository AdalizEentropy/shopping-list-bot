package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.command.model.UpdateContext;
import ru.adaliza.chatbot.keyboard.InlineKeyboardInitializer;
import ru.adaliza.chatbot.language.model.LanguageData;

import java.io.Serializable;

@Service("menuCommand")
@RequiredArgsConstructor
public class MenuCommandService extends AbstractCommandService {
    private final InlineKeyboardInitializer keyboardInitializer;
    private LanguageData languageData;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        languageData = updateContext.languageData();
        return createKeyboardReplyMessage(updateContext, languageData.selectCommand());
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return keyboardInitializer.inlineMainMenuMarkup(languageData);
    }
}
