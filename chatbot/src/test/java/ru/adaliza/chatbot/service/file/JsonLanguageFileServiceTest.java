package ru.adaliza.chatbot.service.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.adaliza.chatbot.property.BotProperties;
import ru.adaliza.chatbot.service.language.model.Language;

@ExtendWith(MockitoExtension.class)
class JsonLanguageFileServiceTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock private BotProperties botProperties;
    private JsonLanguageFileService jsonLanguageFileService;

    @BeforeEach
    void init() {
        jsonLanguageFileService = new JsonLanguageFileService(objectMapper, botProperties);
    }

    @Test
    void testReadFile() {
        when(botProperties.getLanguagesFile())
                .thenReturn("src/main/resources/text-in-languages.json");

        jsonLanguageFileService.readFile();
        Language data = jsonLanguageFileService.getData();
        assertThat(data).isNotNull();
    }
}
