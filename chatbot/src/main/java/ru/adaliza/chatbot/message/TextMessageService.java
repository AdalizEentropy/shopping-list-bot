package ru.adaliza.chatbot.message;

import static ru.adaliza.chatbot.command.BotCommand.ADD;

import java.io.Serializable;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.adaliza.chatbot.keyboard.InlineKeyboardInitializer;
import ru.adaliza.chatbot.model.User;
import ru.adaliza.chatbot.property.BotProperties;
import ru.adaliza.chatbot.service.ProductService;
import ru.adaliza.chatbot.service.UserService;
import ru.adaliza.chatbot.service.UserSettingsService;
import ru.adaliza.chatbot.service.language.LanguageCode;
import ru.adaliza.chatbot.service.language.LanguageConverter;
import ru.adaliza.chatbot.service.language.model.LanguageData;

@Service
@Slf4j
@RequiredArgsConstructor
public class TextMessageService implements MessageService<Serializable> {
    private final UserService userService;
    private final ProductService productService;
    private final UserSettingsService userSettingsService;
    private final BotProperties properties;
    private final LanguageConverter languageConverter;
    private final InlineKeyboardInitializer keyboardInitializer;

    @Override
    public BotApiMethod<Serializable> replyOnMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
        Optional<User> user = userService.getUser(chatId);

        if (user.isPresent() && user.get().getChatPhase() == ADD) {
            LanguageCode languageCode = userSettingsService.getLanguage(chatId);
            int productQuantity = productService.getProductQuantity(chatId);
            LanguageData languageData = languageConverter.getLanguageData(languageCode);
            if (productQuantity >= properties.getMaxProductQuantity()) {
                return createReplyKeyboardMessage(
                        chatId,
                        user.get().getMainMessageId(),
                        languageData.errorAdding(),
                        languageData);
            } else {
                String product = update.getMessage().getText();
                productService.addProduct(chatId, product, languageCode);
                String text = String.format(languageData.added(), product);
                return createReplyKeyboardMessage(
                        chatId, user.get().getMainMessageId(), text, languageData);
            }

        } else {
            log.warn("Inappropriate text message execution.");
            return null;
        }
    }

    protected EditMessageText createReplyKeyboardMessage(
            Long chatId, Integer messageId, String text, LanguageData languageData) {
        var chatIdStr = String.valueOf(chatId);
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatIdStr);
        editMessage.setMessageId(messageId);
        editMessage.setText(text);
        editMessage.setReplyMarkup(keyboardInitializer.inlineInnerMenuMarkup(languageData));

        return editMessage;
    }
}
