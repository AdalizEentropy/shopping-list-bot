package ru.adaliza.chatbot.language;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.objects.User;
import ru.adaliza.chatbot.exception.ReadFileException;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LanguageConverter {
    private final ObjectMapper objectMapper;
    private Language language;

    public void readFile() {
        // TODO убрать в yml
        File file = new File("src/main/resources/text-in-languages.json");

        try {
            language = objectMapper.readValue(file, Language.class);
        } catch (IOException ex) {
            log.error("Error to read file '{}'", file.getName(), ex);
            throw new ReadFileException("Error to read file");
        }
    }

    public LanguageData getLanguageData(User user) {
        LanguageCode languageCode = getLanguageCode(user);
        if (languageCode == LanguageCode.RU) {
            return language.ru();
        } else {
            return language.en();
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
