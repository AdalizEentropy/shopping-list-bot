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

@Service("showCommand")
@RequiredArgsConstructor
public class ShowCommandService extends AbstractCommandService {
    private final ProductService productService;
    private final LanguageConverter languageConverter;
    private final InlineKeyboardInitializer keyboardInitializer;
    private LanguageData languageData;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        var allShoppingList = productService.getAllProducts(updateContext.chatId());
        StringBuilder builder = new StringBuilder();
        languageData = languageConverter.getLanguageData(updateContext.user());
        if (allShoppingList.isEmpty()) {
            builder.append(languageData.emptyList());
        } else {
            builder.append("<b>");
            builder.append(languageData.fullList());
            builder.append("</b>");
            builder.append("\n");
            allShoppingList.forEach(
                    product -> {
                        builder.append("- ");
                        builder.append(product.name());
                        builder.append("\n");
                    });
        }

        return createKeyboardReplyMessage(updateContext, builder.toString());
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return keyboardInitializer.inlineInnerMenuMarkup(languageData);
    }
}
