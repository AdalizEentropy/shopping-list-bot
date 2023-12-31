package ru.adaliza.chatbot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.adaliza.chatbot.ShoppingListBot;
import ru.adaliza.chatbot.property.BotProperties;

@Configuration
@EnableConfigurationProperties(BotProperties.class)
public class BotConfig {

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
}
