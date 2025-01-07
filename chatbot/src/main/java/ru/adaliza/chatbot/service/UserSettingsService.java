package ru.adaliza.chatbot.service;

import ru.adaliza.chatbot.model.UserSettings;
import ru.adaliza.chatbot.service.language.LanguageCode;

public interface UserSettingsService {

    void setSettings(UserSettings settings);

    boolean useCategory(Long userId);

    LanguageCode getLanguage(Long userId);
}
