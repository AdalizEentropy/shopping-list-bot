package ru.adaliza.chatbot;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.adaliza.chatbot.service.file.FileService;
import ru.adaliza.chatbot.service.language.model.Language;

@Component
@RequiredArgsConstructor
public class ChatbotApplicationRunner implements ApplicationRunner {
    private final FileService<Language> languageFileService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        languageFileService.readFile();
    }
}
