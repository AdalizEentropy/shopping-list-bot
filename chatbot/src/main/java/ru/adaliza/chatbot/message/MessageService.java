package ru.adaliza.chatbot.message;

import java.io.Serializable;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageService<T extends Serializable> {

    BotApiMethod<T> replyOnMessage(Update update);
}
