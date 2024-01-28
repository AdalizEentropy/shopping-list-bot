package ru.adaliza.service;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.adaliza.model.*;

@Slf4j
@Service
public class BaseWebService implements WebService {
    private static final String ERROR_CATEGORY = "Other";
    private final WebClient webClient;
    private final JwtService jwtService;

    public BaseWebService(@Qualifier("baseWebClient") WebClient webClient, JwtService jwtService) {
        this.webClient = webClient;
        this.jwtService = jwtService;
    }

    private static WebMessage createProductCategorySysMessage() {
        return new WebMessage(
                WebMessageRole.SYSTEM,
                "Называй только обобщенную категорию товаров. Используй не более трёх слов");
    }

    public Mono<String> getProductCategory(String product) {
        String requestId = String.valueOf(randomUUID());
        log.debug("Get product category: reqId={}, product={}", requestId, product);
        try {
            JwtToken jwtToken = jwtService.getAccessToken();
            return makeRequest(jwtToken.getAccessToken(), requestId, product);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
            return Mono.just(ERROR_CATEGORY);
        }
    }

    private Mono<String> makeRequest(String accessToken, String requestId, String product) {
        return webClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .header("X-Request-ID", requestId)
                .header(AUTHORIZATION, "Bearer " + accessToken)
                .bodyValue(
                        new WebRequest(
                                List.of(
                                        createProductCategorySysMessage(),
                                        new WebMessage(WebMessageRole.USER, product))))
                .retrieve()
                .bodyToMono(WebResponse.class)
                .map(this::mapResponseToCategory)
                .onErrorResume(
                        error -> {
                            log.error("Product category request error. {}", error.getMessage());
                            return Mono.just(ERROR_CATEGORY);
                        });
    }

    private String mapResponseToCategory(WebResponse response) {
        if (response != null) {
            return response.getChoices().stream()
                    .findFirst()
                    .map(choice -> choice.getMessage().getContent())
                    .orElseGet(
                            () -> {
                                log.warn(
                                        "Product category response has incorrect body: {}",
                                        response);
                                return ERROR_CATEGORY;
                            });
        }

        log.warn("Product category response has empty body");
        return ERROR_CATEGORY;
    }
}
