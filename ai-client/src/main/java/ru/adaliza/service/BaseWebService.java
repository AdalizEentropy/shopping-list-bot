package ru.adaliza.service;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.adaliza.model.*;

@Slf4j
@Service
public class BaseWebService implements WebService {
    private final WebClient webClient;
    private final JwtService jwtService;

    public BaseWebService(@Qualifier("baseWebClient") WebClient webClient, JwtService jwtService) {
        this.webClient = webClient;
        this.jwtService = jwtService;
    }

    private static WebMessage createProductCategorySysMessage() {
        return new WebMessage(WebMessageRole.SYSTEM, "Называй только продовольственные категории");
    }

    public Flux<String> getProductCategory(String product) {
        String requestId = String.valueOf(randomUUID());
        log.debug("Get product category: reqId={}, product={}", requestId, product);
        JwtToken jwtToken = jwtService.getAccessToken();

        return webClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .header("X-Request-ID", requestId)
                .header(AUTHORIZATION, "Bearer " + jwtToken.accessToken())
                .bodyValue(
                        new WebRequest(
                                List.of(
                                        createProductCategorySysMessage(),
                                        new WebMessage(WebMessageRole.USER, product))))
                .exchangeToFlux(
                        clientResponse ->
                                clientResponse
                                        .bodyToFlux(WebResponse.class)
                                        .map(this::mapResponseToCategory));
    }

    private String mapResponseToCategory(WebResponse response) {
        if (response != null) {
            return response.getChoices().stream()
                    .findFirst()
                    .map(choice -> choice.getMessage().getContent())
                    .orElse(null);
        }
        return null;
    }
}
