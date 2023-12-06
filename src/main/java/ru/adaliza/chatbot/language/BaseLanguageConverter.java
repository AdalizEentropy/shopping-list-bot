package ru.adaliza.chatbot.language;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import ru.adaliza.chatbot.language.model.Language;
import ru.adaliza.chatbot.language.model.LanguageData;
import ru.adaliza.chatbot.service.file.FileService;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseLanguageConverter implements LanguageConverter {
    private final FileService<Language> languageFileService;

    public LanguageData getLanguageData(User user) {
        LanguageCode languageCode = getLanguageCode(user);
        if (languageCode == LanguageCode.RU) {
            return languageFileService.getData().ru();
        } else {
            return languageFileService.getData().en();
        }
    }

    public LanguageCode getLanguageCode(User user) {
        String languageCode = user.getLanguageCode();
        if (LanguageCode.valueOfCode(languageCode) == LanguageCode.RU) {
            return LanguageCode.RU;
        } else {
            return LanguageCode.EN;
        }
    }
}
