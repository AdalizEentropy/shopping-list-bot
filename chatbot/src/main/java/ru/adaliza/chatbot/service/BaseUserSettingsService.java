package ru.adaliza.chatbot.service;

import java.util.Optional;
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
    public void setSettings(Long chatId, LanguageCode languageCode) {
        Optional<UserSettings> userSettings = userSettingsRepository.findById(chatId);
        if (userSettings.isEmpty()) {
            userSettingsRepository.save(new UserSettings(chatId, languageCode, true, true));
        } else {
            UserSettings updatedSettings = userSettings.get();
            updatedSettings.setUseCategory(true);
            updatedSettings.setLanguage(languageCode);
            userSettingsRepository.save(updatedSettings);
        }
    }

    @Override
    public boolean useCategory(Long userId) {
        return userSettingsRepository.getUseCategoryByUserId(userId);
    }

    @Override
    public void changeUseCategory(Long userId, Boolean enable) {
        Optional<UserSettings> userSettings = userSettingsRepository.findById(userId);
        if (userSettings.isEmpty()) {
            throw new IllegalArgumentException("User settings not found");
        } else {
            UserSettings updatedSettings = userSettings.get();
            updatedSettings.setUseCategory(enable);
            userSettingsRepository.save(updatedSettings);
        }
    }

    @Override
    public LanguageCode getLanguage(Long userId) {
        return userSettingsRepository.getLanguageByUserId(userId);
    }
}
