package ru.adaliza.chatbot;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.adaliza.chatbot.language.LanguageConverter;

@Component
@RequiredArgsConstructor
public class ChatbotApplicationRunner implements ApplicationRunner {
    private final LanguageConverter languageConverter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        languageConverter.readFile();
    }
}
