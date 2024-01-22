package ru.adaliza.service;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import ru.adaliza.model.JwtToken;
import ru.adaliza.properties.WebClientProperties;

@Slf4j
@Service
public class JwtService {
    private final WebClient webClient;
    private final WebClientProperties properties;

    public JwtService(
            @Qualifier("jwtWebClient") WebClient webClient, WebClientProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    public JwtToken getAccessToken() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("scope", properties.getScope());

        // TODO проверять срок действия токена в хранилище
        return webClient
                .post()
                .contentType(APPLICATION_FORM_URLENCODED)
                .accept(MediaType.ALL)
                .bodyValue(formData)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(JwtToken.class))
                .block(Duration.ofSeconds(2));
    }
}
