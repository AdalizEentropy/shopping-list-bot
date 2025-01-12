package ru.adaliza.chatbot.dao;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.adaliza.chatbot.model.UserSettings;
import ru.adaliza.chatbot.service.language.LanguageCode;

public interface UserSettingsRepository extends CrudRepository<UserSettings, Long> {

    @Query("select use_category from user_settings where user_id = :userId")
    boolean getUseCategoryByUserId(Long userId);

    @Query("select app_language from user_settings where user_id = :userId")
    LanguageCode getLanguageByUserId(Long userId);
}
