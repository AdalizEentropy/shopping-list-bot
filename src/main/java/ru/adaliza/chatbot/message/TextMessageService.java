package ru.adaliza.chatbot.message;

import static ru.adaliza.chatbot.command.BotCommand.ADD;
import static ru.adaliza.chatbot.command.BotCommand.START;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.model.User;
import ru.adaliza.chatbot.service.ProductService;
import ru.adaliza.chatbot.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TextMessageService extends AbstractMessageService implements MessageService {
    private final UserService userService;
    private final ProductService productService;

    @Override
    public SendMessage replyOnMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();

        if (START.getTextCommand().equals(message)) {
            String userName = update.getMessage().getChat().getUserName();
            userService.addUser(chatId, userName);
            return replyStartCommand(chatId, userName);
        } else {
            Optional<User> user = userService.getUser(chatId);
            if (user.isPresent() && user.get().getChatPhase().equals(ADD.getTextCommand())) {
                var text = update.getMessage().getText();
                productService.addProduct(chatId, text);
                return createTextWithKeyboardReplyMessage(chatId, "Product was added");
            } else {
                return replyUnknownMessage(chatId);
            }
        }
    }

    private SendMessage replyStartCommand(Long chatId, String userName) {
        String text = String.format("Welcome to the shopping list bot, %s\\!", userName);
        return createKeyboardReplyMessage(chatId, text, Buttons.inlineMainMenuMarkup());
    }

    private SendMessage replyUnknownMessage(Long chatId) {
        String text = "Unknown command\\!";
        return createTextWithKeyboardReplyMessage(chatId, text);
    }
}
