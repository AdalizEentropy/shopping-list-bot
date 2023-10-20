package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adaliza.chatbot.service.ProductService;

@Service
@RequiredArgsConstructor
@Qualifier("showCommand")
public class ShowCommandService extends AbstractBotCommandService {
    private final ProductService productService;

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        var allShoppingList = productService.getAllProducts(chatId);
        StringBuilder builder = new StringBuilder();
        builder.append("*Your shopping list:*");
        builder.append("\n");
        allShoppingList.forEach(
                product -> {
                    builder.append("\\- ");
                    builder.append(product.name());
                    builder.append("\n");
                });
        return createTextReplyMessage(chatId, builder.toString());
    }
}
