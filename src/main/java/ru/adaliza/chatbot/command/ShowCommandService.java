package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.language.LanguageConverter;
import ru.adaliza.chatbot.service.ProductService;

import java.io.Serializable;

@Service("showCommand")
@RequiredArgsConstructor
public class ShowCommandService extends AbstractCommandService {
    private final ProductService productService;
    private final LanguageConverter languageConverter;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        var allShoppingList = productService.getAllProducts(updateContext.chatId());
        StringBuilder builder = new StringBuilder();
        if (allShoppingList.isEmpty()) {
            builder.append(languageConverter.getLanguageData(updateContext.user()).emptyList());
        } else {
            builder.append("<b>");
            builder.append(languageConverter.getLanguageData(updateContext.user()).fullList());
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
        return Buttons.inlineInnerMenuMarkup();
    }
}
