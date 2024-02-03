package ru.adaliza.chatbot.service.language.model;

import lombok.Builder;

@Builder
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
        String help,
        String unknown,
        String errorAdding,
        String otherProduct) {}
