package ru.adaliza.chatbot.language;

public record LanguageData(
        ButtonNames buttons,
        String welcome,
        String add,
        String added,
        String emptyList,
        String fullList,
        String selectCommand,
        String remove,
        String emptyRemove,
        String removeAll,
        String help) {}
