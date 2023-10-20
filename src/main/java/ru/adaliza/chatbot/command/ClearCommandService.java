package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adaliza.chatbot.service.ProductService;

@Service
@Qualifier("clearCommand")
@RequiredArgsConstructor
public class ClearCommandService extends AbstractBotCommandService {
    private final ProductService productService;

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        var text = "All products have been cleared";
        productService.removeAllProducts(chatId);
        return createTextReplyMessage(chatId, text);
    }
}
