package ru.adaliza.chatbot.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageService {

    SendMessage replyOnMessage(Update update);
}
