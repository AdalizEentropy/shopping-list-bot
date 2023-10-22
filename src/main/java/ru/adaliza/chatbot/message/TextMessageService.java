package ru.adaliza.chatbot.message;

import static ru.adaliza.chatbot.command.BotCommand.ADD;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import ru.adaliza.chatbot.model.User;
import ru.adaliza.chatbot.service.ProductService;
import ru.adaliza.chatbot.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TextMessageService extends AbstractTextMessageService
        implements MessageService<Message> {
    private final UserService userService;
    private final ProductService productService;

    @Override
    public BotApiMethod<Message> replyOnMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
        Optional<User> user = userService.getUser(chatId);

        if (user.isPresent() && user.get().getChatPhase() == ADD) {
            String text = update.getMessage().getText();
            productService.addProduct(chatId, text);
            //TODO узнать как можно изменить пришедшее сообщение, а не кнопки, и в идеале отвечать уведомлением
            return createTextReplyMessage(
                    chatId, "Product was added\\. You can add one more or return to main menu");
        } else {
            return replyUnknownMessage(chatId);
        }
    }

    private BotApiMethod<Message> replyUnknownMessage(Long chatId) {
        String text = "Unknown command\\!";
        return createTextReplyMessage(chatId, text);
    }
}
