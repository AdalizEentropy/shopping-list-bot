package ru.adaliza.chatbot.command;

import java.io.Serializable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.adaliza.chatbot.command.model.UpdateContext;
import ru.adaliza.chatbot.keyboard.InlineKeyboardInitializer;
import ru.adaliza.chatbot.model.Product;
import ru.adaliza.chatbot.service.ProductService;

@Service("removeCommand")
@RequiredArgsConstructor
public class RemoveCommandService extends AbstractCommandService {
    private final ProductService productService;
    private List<Product> allShoppingList;
    private final InlineKeyboardInitializer keyboardInitializer;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        if (!updateContext.command().equals(BotCommand.REMOVE.getCommand())) {
            removeProduct(updateContext.command());
        }

        var text = updateContext.languageData().remove();
        allShoppingList = productService.getAllProducts(updateContext.chatId());
        if (allShoppingList.isEmpty()) {
            text = updateContext.languageData().emptyRemove();
        }
        return createKeyboardReplyMessage(updateContext, text);
    }

    private void removeProduct(String command) {
        productService.removeProductById(Long.valueOf(command));
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return keyboardInitializer.inlineProductsMarkup(allShoppingList);
    }
}
