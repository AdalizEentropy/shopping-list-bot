package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.language.LanguageConverter;

import java.io.Serializable;

@Service("helpCommand")
@RequiredArgsConstructor
public class HelpCommandService extends AbstractCommandService {
    private final LanguageConverter languageConverter;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        String text = languageConverter.getLanguageData(updateContext.user()).help();
        return createKeyboardReplyMessage(updateContext, text);
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineInnerMenuMarkup();
    }
}
