package ru.adaliza.service;

import static java.util.UUID.randomUUID;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.adaliza.exception.WebRequestException;
import ru.adaliza.model.JwtToken;
import ru.adaliza.properties.WebClientProperties;

@Slf4j
@Service
public class JwtService {
    private final WebClient webClient;
    private final WebClientProperties properties;
    private final ReentrantLock lock;
    private JwtToken jwtToken;

    public JwtService(
            @Qualifier("jwtWebClient") WebClient webClient, WebClientProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
        this.lock = new ReentrantLock();
    }

    public JwtToken getAccessToken() {
        lock.lock();
        try {
            if (isJwtTokenValid()) {
                log.debug("Get jwtToken from memory");
                return jwtToken;
            } else {
                return updateJwtToken();
            }
        } catch (WebRequestException ex) {
            log.error(ex.getMessage());
            throw ex;
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
            throw new WebRequestException(ex.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private boolean isJwtTokenValid() {
        if (jwtToken == null) {
            return false;
        } else {
            Instant tokenExp = Instant.ofEpochMilli(jwtToken.getExpiresAt());
            return !tokenExp.isBefore(Instant.now());
        }
    }

    private JwtToken updateJwtToken() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("scope", properties.getScope());
        String requestId = String.valueOf(randomUUID());
        log.debug("Get jwtToken from server: reqId={}", requestId);

        return webClient
                .post()
                .contentType(APPLICATION_FORM_URLENCODED)
                .accept(MediaType.ALL)
                .header("RqUID", requestId)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(JwtToken.class)
                .onErrorResume(
                        error ->
                                Mono.error(
                                        new WebRequestException(
                                                "Jwt token request error. " + error.getMessage())))
                .doOnSuccess(
                        token -> {
                            jwtToken = token;
                            log.debug("JwtToken was updated");
                        })
                .block(properties.getResponseTimeout());
    }
}
