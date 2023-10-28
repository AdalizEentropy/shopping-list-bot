package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.property.BotProperties;
import ru.adaliza.chatbot.service.ProductService;
import ru.adaliza.chatbot.service.UserService;

import java.io.Serializable;

@Service("addCommand")
@RequiredArgsConstructor
public class AddCommandService extends AbstractCommandService {
    public static final String FOR_ADDING = "Enter product name for adding";
    public static final String ERROR_ADDING = "Product quantity exceeded";
    private final UserService userService;
    private final ProductService productService;
    private final BotProperties properties;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(ButtonData buttonData) {
        int productQuantity = productService.getProductQuantity(buttonData.chatId());
        if (productQuantity >= properties.getMaxProductQuantity()) {
            return createKeyboardReplyMessage(buttonData, ERROR_ADDING);
        } else {
            userService.updateMainMessageId(buttonData.chatId(), buttonData.messageId());
            return createKeyboardReplyMessage(buttonData, FOR_ADDING);
        }
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineInnerMenuMarkup();
    }
}
