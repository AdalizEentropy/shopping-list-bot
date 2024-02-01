package ru.adaliza.chatbot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.adaliza.chatbot.ShoppingListBot;
import ru.adaliza.chatbot.property.AiClientProperties;
import ru.adaliza.chatbot.property.BotProperties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({BotProperties.class, AiClientProperties.class})
public class BotConfig {
    private final AiClientProperties aiClientProperties;

    @Bean
    public TelegramBotsApi telegramBotsApi(ShoppingListBot shoppingListBot)
            throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(shoppingListBot);
        return api;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(aiClientProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector())
                .build();
    }
}
