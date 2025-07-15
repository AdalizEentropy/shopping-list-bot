package ru.adaliza.service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.adaliza.exception.ReadFileException;
import ru.adaliza.properties.WebClientProperties;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromptFileService {
    private final WebClientProperties webClientProperties;
    @Getter String prompt;

    @PostConstruct
    public void init() {
        Path file = webClientProperties.getPromptFile();

        try {
            prompt = String.join("\n", Files.readAllLines(file));
        } catch (IOException ex) {
            log.error("Error to read file '{}'", file.getFileName(), ex);
            throw new ReadFileException("Error to read file");
        }
    }
}
