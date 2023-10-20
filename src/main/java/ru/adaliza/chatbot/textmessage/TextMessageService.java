package ru.adaliza.chatbot.textmessage;

import static ru.adaliza.chatbot.command.AddCommandService.FOR_ADDING;
import static ru.adaliza.chatbot.command.BotCommand.START;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.service.ProductService;
import ru.adaliza.chatbot.service.UserService;

@Service
@RequiredArgsConstructor
public class TextMessageService implements BotTextMessageService {
    private final UserService userService;
    private final ProductService productService;

    @Override
    public SendMessage replyOnTextMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();

        if (START.getTextCommand().equals(message)) {
            String userName = update.getMessage().getChat().getUserName();
            userService.addUser(chatId, userName);
            return createStartCommand(chatId, userName);
        } else if (FOR_ADDING.equals(update.getMessage().getReplyToMessage().getText())) {
            var text = update.getMessage().getText();
            productService.addProduct(chatId, text);
            return createReplyMessage(chatId, "Product was added");
        } else {
            return answerUnknownMessage(chatId);
        }
    }

    private SendMessage createStartCommand(Long chatId, String userName) {
        String text = String.format("Welcome to the shopping list bot, %s!", userName);
        return createReplyMessage(chatId, text);
    }

    private SendMessage answerUnknownMessage(Long chatId) {
        String text = "Unknown command!";
        return createReplyMessage(chatId, text);
    }

    private SendMessage createReplyMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        sendMessage.setReplyMarkup(Buttons.inlineMainMenuMarkup());

        return sendMessage;
    }
}
