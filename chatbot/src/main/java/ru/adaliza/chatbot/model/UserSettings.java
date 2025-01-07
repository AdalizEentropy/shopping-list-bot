package ru.adaliza.chatbot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.adaliza.chatbot.service.language.LanguageCode;

@Table("user_settings")
public record UserSettings(
        @Id Long userId,
        @Column(value = "app_language") LanguageCode language,
        Boolean useCategory,
        @Transient boolean isNew)
        implements Persistable<Long> {

    @Override
    public Long getId() {
        return userId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PersistenceCreator
    public UserSettings(Long userId, LanguageCode language, Boolean useCategory) {
        this(userId, language, useCategory, false);
    }
}
