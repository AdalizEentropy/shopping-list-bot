package ru.adaliza.chatbot.command;

import java.io.Serializable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.adaliza.chatbot.command.model.UpdateContext;
import ru.adaliza.chatbot.keyboard.InlineKeyboardInitializer;
import ru.adaliza.chatbot.model.Product;
import ru.adaliza.chatbot.service.ProductService;
import ru.adaliza.chatbot.service.language.model.LanguageData;

@Service("showCommand")
@RequiredArgsConstructor
public class ShowCommandService extends AbstractCommandService {
    private final ProductService productService;
    private final InlineKeyboardInitializer keyboardInitializer;
    private LanguageData languageData;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        var allShoppingList = productService.getAllProducts(updateContext.chatId());
        StringBuilder builder = new StringBuilder();
        languageData = updateContext.languageData();
        if (allShoppingList.isEmpty()) {
            builder.append(languageData.emptyList());
        } else {
            builder.append("<b><u>");
            builder.append(languageData.fullList());
            builder.append("</u></b>");
            builder.append("\n");
            sortProducts(allShoppingList, builder);
        }

        return createKeyboardReplyMessage(updateContext, builder.toString());
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return keyboardInitializer.inlineInnerMenuMarkup(languageData);
    }

    private void sortProducts(List<Product> allShoppingList, StringBuilder builder) {
        String category = null;
        for (Product product : allShoppingList) {
            var prodCategory =
                    StringUtils.isEmpty(product.category())
                            ? languageData.otherProduct()
                            : product.category();
            if (category == null || !category.equals(prodCategory)) {
                category = prodCategory;
                builder.append("\n<i>");
                builder.append(category);
                builder.append("</i>\n");
            }
            builder.append("- ");
            builder.append(product.name());
            builder.append("\n");
        }
    }
}
