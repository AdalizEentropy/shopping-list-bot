package ru.adaliza.chatbot;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.adaliza.chatbot.message.ButtonService;
import ru.adaliza.chatbot.property.BotProperties;
import ru.adaliza.chatbot.message.MessageService;
import ru.adaliza.chatbot.message.TextMessageService;

@Slf4j
@Component
public class ShoppingListBot extends TelegramLongPollingBot {
    private final BotProperties properties;
    private final MessageService textMessageService;
    private final MessageService buttonService;

    @Autowired
    public ShoppingListBot(
            BotProperties properties,
            TextMessageService textMessageService,
            ButtonService buttonService) {
        super(properties.getToken());
        this.properties = properties;
        this.textMessageService = textMessageService;
        this.buttonService = buttonService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            setMdc(update.getMessage().getChatId());
            log.debug("Text message received: '{}'", update.getMessage().getText());
            SendMessage replyMessage = textMessageService.replyOnMessage(update);
            executeMessage(replyMessage);
        } else if (update.hasCallbackQuery()) {
            setMdc(update.getCallbackQuery().getMessage().getChatId());
            log.debug("CallbackQuery received: '{}'", update.getCallbackQuery().getData());
            SendMessage replyMessage = buttonService.replyOnMessage(update);
            executeMessage(replyMessage);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Unexpected message type received");
            }
        }
    }

    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    private void executeMessage(SendMessage replyMessage) {
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
