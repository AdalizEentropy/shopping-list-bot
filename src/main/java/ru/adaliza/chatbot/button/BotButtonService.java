package ru.adaliza.chatbot.button;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotButtonService {

    SendMessage replyOnCommand(Update update);
}
