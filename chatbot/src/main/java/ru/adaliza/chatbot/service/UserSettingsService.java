package ru.adaliza.chatbot.service;

import ru.adaliza.chatbot.service.language.LanguageCode;

public interface UserSettingsService {

    void setSettings(Long chatId, LanguageCode languageCode);

    boolean useCategory(Long userId);

    LanguageCode getLanguage(Long userId);
}
