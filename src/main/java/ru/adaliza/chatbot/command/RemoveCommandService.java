package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.model.Product;
import ru.adaliza.chatbot.service.ProductService;

import java.io.Serializable;
import java.util.List;

@Service("removeCommand")
@RequiredArgsConstructor
public class RemoveCommandService extends AbstractCommandService {
    private final ProductService productService;
    private List<Product> allShoppingList;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(ButtonData buttonData) {
        var text = "Choose product for removing";
        allShoppingList = productService.getAllProducts(buttonData.chatId());
        if (allShoppingList.isEmpty()) {
            text = "There are no products for removing";
        }
        return createKeyboardReplyMessage(buttonData, text);
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineProductsMarkup(allShoppingList);
    }
}
