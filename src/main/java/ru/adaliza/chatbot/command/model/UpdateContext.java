package ru.adaliza.chatbot.command.model;

import ru.adaliza.chatbot.language.model.LanguageData;

public record UpdateContext(Long chatId, Integer messageId, String command, LanguageData languageData) {}
