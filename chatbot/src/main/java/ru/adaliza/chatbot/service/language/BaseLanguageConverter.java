package ru.adaliza.chatbot.service.language;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.adaliza.chatbot.service.file.FileService;
import ru.adaliza.chatbot.service.language.model.Language;
import ru.adaliza.chatbot.service.language.model.LanguageData;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseLanguageConverter implements LanguageConverter {
    private final FileService<Language> languageFileService;

    @Override
    public LanguageData getLanguageData(User user) {
        LanguageCode languageCode = getLanguageCode(user);
        return getLanguageData(languageCode);
    }

    @Override
    public LanguageData getLanguageData(LanguageCode language) {
        if (language == LanguageCode.RU) {
            return languageFileService.getData().ru();
        } else {
            return languageFileService.getData().en();
        }
    }

    @Override
    public LanguageCode getLanguageCode(User user) {
        String languageCode = user.getLanguageCode();
        if (LanguageCode.valueOfCode(languageCode) == LanguageCode.RU) {
            return LanguageCode.RU;
        } else {
            return LanguageCode.EN;
        }
    }
}
