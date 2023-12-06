package ru.adaliza.chatbot.service.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.adaliza.chatbot.exception.ReadFileException;
import ru.adaliza.chatbot.language.model.Language;
import ru.adaliza.chatbot.property.BotProperties;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class JsonLanguageFileService implements FileService<Language> {
    private final ObjectMapper objectMapper;
    private final BotProperties botProperties;
    private Language language;

    @Override
    public void readFile() {
        File file = new File(botProperties.getLanguagesFile());

        try {
            language = objectMapper.readValue(file, Language.class);
        } catch (IOException ex) {
            log.error("Error to read file '{}'", file.getName(), ex);
            throw new ReadFileException("Error to read file");
        }
    }

    @Override
    public Language getData() {
        return language;
    }
}
