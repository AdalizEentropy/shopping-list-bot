package ru.adaliza.chatbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.adaliza.chatbot.dao.UserSettingsRepository;
import ru.adaliza.chatbot.model.UserSettings;
import ru.adaliza.chatbot.service.language.LanguageCode;

@Service
@RequiredArgsConstructor
public class BaseUserSettingsService implements UserSettingsService {
    private final UserSettingsRepository userSettingsRepository;

    @Override
    public void setSettings(UserSettings settings) {
        userSettingsRepository.save(settings);
    }

    @Override
    public boolean useCategory(Long userId) {
        return userSettingsRepository.getUseCategoryByUserId(userId);
    }

    @Override
    public LanguageCode getLanguage(Long userId) {
        return userSettingsRepository.getLanguageByUserId(userId);
    }
}
