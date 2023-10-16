package ru.adaliza.chatbot.bot;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.adaliza.chatbot.command.BotCommandService;
import ru.adaliza.chatbot.noncommand.BotTextMessageService;
import ru.adaliza.chatbot.property.BotProperties;

@Slf4j
@Component
public class ShoppingListBot extends TelegramLongPollingBot {
    private final BotProperties properties;
    private final BotTextMessageService textMessageService;
    private final BotCommandService commandService;

    @Autowired
    public ShoppingListBot(
            BotProperties properties,
            BotTextMessageService textMessageService,
            BotCommandService commandService) {
        super(properties.getToken());
        this.properties = properties;
        this.textMessageService = textMessageService;
        this.commandService = commandService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage replyMessage = textMessageService.replyOnTextMessage(update);
            sendMessage(replyMessage);
        } else if (update.hasCallbackQuery()) {
            SendMessage replyMessage = commandService.replyOnCommand(update);
            sendMessage(replyMessage);
        } else {
            // nothing to do
        }
    }

    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    private void sendMessage(SendMessage replyMessage) {
        try {
            execute(replyMessage);
        } catch (TelegramApiException e) {
            log.error("Error to send message", e);
        }
    }
}
