package ru.adaliza.chatbot.service.language;

import org.telegram.telegrambots.meta.api.objects.User;
import ru.adaliza.chatbot.service.language.model.LanguageData;

public interface LanguageConverter {

    LanguageData getLanguageData(User user);

    LanguageCode getLanguageCode(User user);
}
