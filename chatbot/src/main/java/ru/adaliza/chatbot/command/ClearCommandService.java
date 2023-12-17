package ru.adaliza.chatbot.command;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.adaliza.chatbot.command.model.UpdateContext;
import ru.adaliza.chatbot.keyboard.InlineKeyboardInitializer;
import ru.adaliza.chatbot.service.ProductService;
import ru.adaliza.chatbot.service.language.model.LanguageData;

@Service("clearCommand")
@RequiredArgsConstructor
public class ClearCommandService extends AbstractCommandService {
    private final ProductService productService;
    private final InlineKeyboardInitializer keyboardInitializer;
    private LanguageData languageData;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        languageData = updateContext.languageData();
        productService.removeAllProducts(updateContext.chatId());
        return createKeyboardReplyMessage(updateContext, languageData.removeAll());
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return keyboardInitializer.inlineInnerMenuMarkup(languageData);
    }
}
