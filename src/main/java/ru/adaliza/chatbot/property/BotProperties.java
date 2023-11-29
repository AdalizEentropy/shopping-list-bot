package ru.adaliza.chatbot.property;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties("telegram.bot")
public class BotProperties {
    private final String username;
    private final String token;
    private final Integer maxProductQuantity;
    private final String languagesFile;
}
