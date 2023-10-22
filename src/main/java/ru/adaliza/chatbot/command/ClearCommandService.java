package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.service.ProductService;

import java.io.Serializable;

@Service("clearCommand")
@RequiredArgsConstructor
public class ClearCommandService extends AbstractCommandService {
    private final ProductService productService;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(ButtonData buttonData) {
        var text = "All products have been cleared";
        productService.removeAllProducts(buttonData.chatId());
        return createKeyboardReplyMessage(buttonData, text);
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineInnerMenuMarkup();
    }
}
