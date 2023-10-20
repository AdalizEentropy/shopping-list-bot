package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import ru.adaliza.chatbot.message.AbstractMessageService;
import ru.adaliza.chatbot.service.ProductService;

@Service("removeCommand")
@RequiredArgsConstructor
public class RemoveCommandService extends AbstractMessageService implements BotCommandService {
    private final ProductService productService;

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        var text = "Choose product for remove";
        var allShoppingList = productService.getAllProducts(chatId);
        return createProductsKeyboardReplyMessage(chatId, text, allShoppingList);
    }
}
