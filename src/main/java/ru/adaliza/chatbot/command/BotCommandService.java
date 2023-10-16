package ru.adaliza.chatbot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface BotCommandService {

    SendMessage createMessageForCommand(Long chatId);
}
