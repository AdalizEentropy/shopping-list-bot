package ru.adaliza.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties("ai-client")
public class WebClientProperties {
    private final String baseUrl;
}
