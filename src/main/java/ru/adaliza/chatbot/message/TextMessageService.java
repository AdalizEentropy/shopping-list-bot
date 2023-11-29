package ru.adaliza.chatbot.message;

import static ru.adaliza.chatbot.command.AddCommandService.ERROR_ADDING;
import static ru.adaliza.chatbot.command.BotCommand.ADD;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.language.LanguageConverter;
import ru.adaliza.chatbot.model.User;
import ru.adaliza.chatbot.property.BotProperties;
import ru.adaliza.chatbot.service.ProductService;
import ru.adaliza.chatbot.service.UserService;

import java.io.Serializable;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TextMessageService implements MessageService<Serializable> {
    private final UserService userService;
    private final ProductService productService;
    private final BotProperties properties;
    private final LanguageConverter languageConverter;

    @Override
    public BotApiMethod<Serializable> replyOnMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
        Optional<User> user = userService.getUser(chatId);

        if (user.isPresent() && user.get().getChatPhase() == ADD) {
            int productQuantity = productService.getProductQuantity(chatId);
            if (productQuantity >= properties.getMaxProductQuantity()) {
                return createReplyKeyboardMessage(
                        chatId, user.get().getMainMessageId(), ERROR_ADDING);
            } else {
                String product = update.getMessage().getText();
                productService.addProduct(chatId, product);
                String text =
                        String.format(
                                languageConverter
                                        .getLanguageData(update.getMessage().getFrom())
                                        .added(),
                                product);
                return createReplyKeyboardMessage(chatId, user.get().getMainMessageId(), text);
            }

        } else {
            log.warn("Inappropriate text message execution.");
            return null;
        }
    }

    protected EditMessageText createReplyKeyboardMessage(
            Long chatId, Integer messageId, String text) {
        var chatIdStr = String.valueOf(chatId);
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatIdStr);
        editMessage.setMessageId(messageId);
        editMessage.setText(text);
        editMessage.setReplyMarkup(Buttons.inlineInnerMenuMarkup());

        return editMessage;
    }
}
