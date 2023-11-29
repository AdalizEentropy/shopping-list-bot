package ru.adaliza.chatbot.message;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

public interface MessageService<T extends Serializable> {

    BotApiMethod<T> replyOnMessage(Update update);
}
