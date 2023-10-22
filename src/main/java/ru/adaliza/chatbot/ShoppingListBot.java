package ru.adaliza.chatbot;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.adaliza.chatbot.message.ButtonService;
import ru.adaliza.chatbot.message.MessageService;
import ru.adaliza.chatbot.message.TextCommandService;
import ru.adaliza.chatbot.message.TextMessageService;
import ru.adaliza.chatbot.property.BotProperties;

import java.io.Serializable;

@Slf4j
@Component
public class ShoppingListBot extends TelegramLongPollingBot {
    private final BotProperties properties;
    private final MessageService<Message> textMessageService;
    private final MessageService<Serializable> buttonService;
    private final MessageService<Message> textCommandService;

    @Autowired
    public ShoppingListBot(
            BotProperties properties,
            TextMessageService textMessageService,
            ButtonService buttonService,
            TextCommandService textCommandService) {
        super(properties.getToken());
        this.properties = properties;
        this.textMessageService = textMessageService;
        this.buttonService = buttonService;
        this.textCommandService = textCommandService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            onUpdateMessageReceived(update);
        }

        if (update.hasCallbackQuery()) {
            onUpdateCallbackQueryReceived(update);
        }
    }

    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    private void onUpdateMessageReceived(Update update) {
        if (update.getMessage().isCommand()) {
            setMdc(update.getMessage().getChatId());
            log.debug("Text command received: '{}'", update.getMessage().getText());
            BotApiMethod<Message> replyMessage = textCommandService.replyOnMessage(update);
            executeMessage(replyMessage);
        } else if (update.getMessage().hasText()) {
            setMdc(update.getMessage().getChatId());
            log.debug("Text message received: '{}'", update.getMessage().getText());
            BotApiMethod<Message> replyMessage = textMessageService.replyOnMessage(update);
            executeMessage(replyMessage);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Unexpected message type received");
            }
        }
    }

    private void onUpdateCallbackQueryReceived(Update update) {
        setMdc(update.getCallbackQuery().getMessage().getChatId());
        log.debug("CallbackQuery received: '{}'", update.getCallbackQuery().getData());
        BotApiMethod<Serializable> replyMessage = buttonService.replyOnMessage(update);
        executeMessage(replyMessage);
    }

    private void executeMessage(BotApiMethod<? extends Serializable> replyMessage) {
        try {
            execute(replyMessage);
        } catch (TelegramApiException e) {
            log.error("Error to send message", e);
        }
    }

    private void setMdc(Long chatId) {
        MDC.put("chatId", chatId + ": ");
    }
}
