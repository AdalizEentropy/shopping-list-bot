package ru.adaliza.chatbot.dao;

import org.springframework.data.repository.CrudRepository;
import ru.adaliza.chatbot.model.UserSettings;
import ru.adaliza.chatbot.service.language.LanguageCode;

public interface UserSettingsRepository extends CrudRepository<UserSettings, Long> {

    boolean getUseCategoryByUserId(Long userId);

    LanguageCode getLanguageByUserId(Long userId);
}
