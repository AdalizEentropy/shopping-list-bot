package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.model.Product;
import ru.adaliza.chatbot.service.ProductService;

import java.util.List;

@Service
@Qualifier("removeCommand")
@RequiredArgsConstructor
public class RemoveCommandService extends AbstractBotCommandService {
    private final ProductService productService;

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        var text = "Choose product for remove";
        var allShoppingList = productService.getAllProducts(chatId);
        return createButtonReplyMessage(chatId, text, allShoppingList);
    }

    private SendMessage createButtonReplyMessage(Long chatId, String text, List<Product> products) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        sendMessage.setReplyMarkup(Buttons.inlineProductsMarkup(products));

        return sendMessage;
    }
}
