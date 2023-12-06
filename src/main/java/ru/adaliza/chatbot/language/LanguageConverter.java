package ru.adaliza.chatbot.language;

import org.telegram.telegrambots.meta.api.objects.User;

import ru.adaliza.chatbot.language.model.LanguageData;

public interface LanguageConverter {

    LanguageData getLanguageData(User user);

    LanguageCode getLanguageCode(User user);
}
