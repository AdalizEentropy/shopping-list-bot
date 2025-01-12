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
import ru.adaliza.chatbot.keyboard.InlineKeyboardInitializer;
import ru.adaliza.chatbot.service.UserService;
import ru.adaliza.chatbot.service.UserSettingsService;
import ru.adaliza.chatbot.service.language.LanguageConverter;
import ru.adaliza.chatbot.service.language.model.LanguageData;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextCommandService implements MessageService<Message> {
    private final UserService userService;
    private final UserSettingsService userSettingsService;
    private final LanguageConverter languageConverter;
    private final InlineKeyboardInitializer keyboardInitializer;

    @Override
    public SendMessage replyOnMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();

        if (START.getCommand().equals(messageText)) {
            String userName = update.getMessage().getChat().getUserName();
            userService.addUser(chatId, userName);
            userSettingsService.setSettings(
                    chatId, languageConverter.getLanguageCode(update.getMessage().getFrom()));
            log.info("New user with chatId {} was connected", chatId);
            return replyStartCommand(chatId, userName, update);
        } else {
            log.warn("Inappropriate text command execution.");
            return null;
        }
    }

    private SendMessage replyStartCommand(Long chatId, String userName, Update update) {
        LanguageData languageData =
                languageConverter.getLanguageData(update.getMessage().getFrom());
        String convertedText = languageData.welcome();
        String text =
                StringUtils.isEmpty(userName)
                        ? String.format("%s!", convertedText)
                        : String.format("%s, %s!", convertedText, userName);
        return createReplyKeyboardMessage(
                chatId, text, keyboardInitializer.inlineMainMenuMarkup(languageData));
    }

    private SendMessage createReplyKeyboardMessage(
            Long chatId, String text, ReplyKeyboard keyboard) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        sendMessage.setReplyMarkup(keyboard);

        return sendMessage;
    }
}
