package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.InlineKeyboardInitializer;
import ru.adaliza.chatbot.language.LanguageConverter;
import ru.adaliza.chatbot.language.LanguageData;
import ru.adaliza.chatbot.service.ProductService;

import java.io.Serializable;

@Service("clearCommand")
@RequiredArgsConstructor
public class ClearCommandService extends AbstractCommandService {
    private final ProductService productService;
    private final LanguageConverter languageConverter;
    private final InlineKeyboardInitializer keyboardInitializer;
    private LanguageData languageData;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        languageData = languageConverter.getLanguageData(updateContext.user());
        productService.removeAllProducts(updateContext.chatId());
        return createKeyboardReplyMessage(updateContext, languageData.removeAll());
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return keyboardInitializer.inlineInnerMenuMarkup(languageData);
    }
}
