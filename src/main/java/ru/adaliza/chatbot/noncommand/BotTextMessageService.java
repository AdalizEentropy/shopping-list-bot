package ru.adaliza.chatbot.noncommand;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotTextMessageService {

    SendMessage replyOnTextMessage(Update update);
}
