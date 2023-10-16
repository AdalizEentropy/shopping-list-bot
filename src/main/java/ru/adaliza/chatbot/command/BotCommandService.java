package ru.adaliza.chatbot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotCommandService {

    SendMessage replyOnCommand(Update update);
}
