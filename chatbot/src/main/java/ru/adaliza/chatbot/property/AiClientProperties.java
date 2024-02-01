package ru.adaliza.chatbot.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties("ai-client")
public class AiClientProperties {
    private final String url;
    private final String responseTimeout;
}
