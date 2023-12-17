package ru.adaliza.chatbot.service.language;

import static java.util.Locale.ENGLISH;

import java.util.stream.Stream;

public enum LanguageCode {
    RU,
    EN;

    public static LanguageCode valueOfCode(String value) {
        return Stream.of(LanguageCode.values())
                .filter(v -> v.name().equals(value.toUpperCase(ENGLISH)))
                .findFirst()
                .orElse(EN);
    }
}
