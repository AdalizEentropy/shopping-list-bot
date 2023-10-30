package ru.adaliza.chatbot.message;

import static ru.adaliza.chatbot.command.BotCommand.START;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import ru.adaliza.chatbot.button.Buttons;
import ru.adaliza.chatbot.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextCommandService implements MessageService<Message> {
    private final UserService userService;

    @Override
    public SendMessage replyOnMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();

        if (START.getCommand().equals(message)) {
            String userName = update.getMessage().getChat().getUserName();
            userService.addUser(chatId, userName);
            log.info("New user with chatId {} was connected", chatId);
            return replyStartCommand(chatId, userName);
        } else {
            log.warn("Inappropriate text command execution.");
            return null;
        }
    }

    private SendMessage replyStartCommand(Long chatId, String userName) {
        userName = StringUtils.isEmpty(userName) ? "dear user" : userName;
        String text = String.format("Welcome to the shopping list bot, %s!", userName);
        return createReplyKeyboardMessage(chatId, text, Buttons.inlineMainMenuMarkup());
    }

    private SendMessage createReplyKeyboardMessage(
            Long chatId, String text, ReplyKeyboard keyboard) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        sendMessage.setReplyMarkup(keyboard);

        return sendMessage;
    }
}
