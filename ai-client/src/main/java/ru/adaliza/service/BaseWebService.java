package ru.adaliza.service;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.List;
import java.util.Objects;
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

    public Mono<String> getProductCategory(String product, String lang) {
        try {
            JwtToken jwtToken = jwtService.getAccessToken();
            return makeRequest(jwtToken.getAccessToken(), product, lang);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
            return Mono.just(ERROR_CATEGORY);
        }
    }

    private WebMessage createProductCategorySysMessage(String lang) {
        String languageResult;
        if ("RU".equals(lang)) {
            languageResult = "продукты, одежда, бытовая химия, товары для дома, прочее. ";
        } else {
            languageResult = "products, clothes, household chemicals, household products, other. ";
        }

        return new WebMessage(
                WebMessageRole.SYSTEM,
                "Я хочу создать интернет-магазин, где товары разделены только на следующие категории: "
                        + languageResult
                        + "Назови, к какой из моих перечисленных категорий относится товар. "
                        + "Называй только категорию. "
                        + "Не используй слово \"категория\".");
    }

    private Mono<String> makeRequest(String accessToken, String product, String lang) {
        String requestId = String.valueOf(randomUUID());
        log.debug("Get product category: reqId={}, product={}", requestId, product);

        return webClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .header("X-Request-ID", requestId)
                .header(AUTHORIZATION, "Bearer " + accessToken)
                .bodyValue(
                        new WebRequest(
                                List.of(
                                        createProductCategorySysMessage(lang),
                                        new WebMessage(WebMessageRole.USER, product))))
                .retrieve()
                .bodyToMono(WebResponse.class)
                .map(this::mapResponseToCategory)
                .single()
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
                    .map(Choice::getMessage)
                    .filter(Objects::nonNull)
                    .map(WebMessage::getContent)
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
