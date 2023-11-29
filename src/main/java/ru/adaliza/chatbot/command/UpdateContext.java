package ru.adaliza.chatbot.command;

import org.telegram.telegrambots.meta.api.objects.User;

public record UpdateContext(Long chatId, Integer messageId, String command, User user) {}
