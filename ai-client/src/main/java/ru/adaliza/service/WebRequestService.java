package ru.adaliza.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.adaliza.module.WebMessage;
import ru.adaliza.module.WebMessageRole;
import ru.adaliza.module.WebRequest;

@Service
@RequiredArgsConstructor
public class WebRequestService {
    private static final String SYS_CONTENT = "Называй только продовольственные категории";
    private final WebClient webClient;
    private String accessToken;

    private static WebMessage createSysMessage() {
        return new WebMessage(WebMessageRole.SYSTEM, SYS_CONTENT);
    }

    public Flux<String> getCategory(String product) {
        return webClient
                .post()
                .uri("/accounts/status/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                //                .header("X-Request-ID", "79e41a5f-f180-4c7a-b2d9-393086ae20a1")
                //                .authorization()
                .bodyValue(
                        new WebRequest(
                                List.of(
                                        createSysMessage(),
                                        new WebMessage(WebMessageRole.USER, product))))
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(String.class));
    }
}
