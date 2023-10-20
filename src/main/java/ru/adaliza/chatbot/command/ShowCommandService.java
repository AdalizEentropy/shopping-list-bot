package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import ru.adaliza.chatbot.message.AbstractMessageService;
import ru.adaliza.chatbot.service.ProductService;

@Service("showCommand")
@RequiredArgsConstructor
public class ShowCommandService extends AbstractMessageService implements BotCommandService {
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
        return createTextWithKeyboardReplyMessage(chatId, builder.toString());
    }
}
