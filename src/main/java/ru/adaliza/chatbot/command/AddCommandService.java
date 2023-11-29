package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.language.LanguageConverter;
import ru.adaliza.chatbot.property.BotProperties;
import ru.adaliza.chatbot.service.ProductService;
import ru.adaliza.chatbot.service.UserService;

import java.io.Serializable;

@Service("addCommand")
@RequiredArgsConstructor
public class AddCommandService extends AbstractCommandService {
    public static final String ERROR_ADDING = "Product quantity exceeded";
    private final UserService userService;
    private final ProductService productService;
    private final BotProperties properties;
    private final LanguageConverter languageConverter;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        int productQuantity = productService.getProductQuantity(updateContext.chatId());
        if (productQuantity >= properties.getMaxProductQuantity()) {
            return createKeyboardReplyMessage(updateContext, ERROR_ADDING);
        } else {
            userService.updateMainMessageId(updateContext.chatId(), updateContext.messageId());
            return createKeyboardReplyMessage(
                    updateContext, languageConverter.getLanguageData(updateContext.user()).add());
        }
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineInnerMenuMarkup();
    }
}
