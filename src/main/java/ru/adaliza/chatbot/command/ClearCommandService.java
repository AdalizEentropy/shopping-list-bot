package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.language.LanguageConverter;
import ru.adaliza.chatbot.service.ProductService;

import java.io.Serializable;

@Service("clearCommand")
@RequiredArgsConstructor
public class ClearCommandService extends AbstractCommandService {
    private final ProductService productService;
    private final LanguageConverter languageConverter;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        var text = languageConverter.getLanguageData(updateContext.user()).removeAll();
        productService.removeAllProducts(updateContext.chatId());
        return createKeyboardReplyMessage(updateContext, text);
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineInnerMenuMarkup();
    }
}
