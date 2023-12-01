package ru.adaliza.chatbot.command;

import ru.adaliza.chatbot.language.LanguageData;

public record UpdateContext(Long chatId, Integer messageId, String command, LanguageData languageData) {}
