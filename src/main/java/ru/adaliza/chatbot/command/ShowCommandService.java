package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.service.ProductService;

import java.io.Serializable;

@Service("showCommand")
@RequiredArgsConstructor
public class ShowCommandService extends AbstractCommandService {
    private final ProductService productService;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(ButtonData buttonData) {
        var allShoppingList = productService.getAllProducts(buttonData.chatId());
        StringBuilder builder = new StringBuilder();
        if (allShoppingList.isEmpty()) {
            builder.append("Your shopping list is empty");
        } else {
            builder.append("*Your shopping list:*");
            builder.append("\n");
            allShoppingList.forEach(
                    product -> {
                        builder.append("\\- ");
                        builder.append(product.name());
                        builder.append("\n");
                    });
        }

        return createKeyboardReplyMessage(buttonData, builder.toString());
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineInnerMenuMarkup();
    }
}
