package ru.adaliza.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import ru.adaliza.model.JwtToken;

@SpringBootTest
class JwtServiceTest {
    @Autowired private JwtService jwtService;

    @Test
    void testtt() {
        Flux<JwtToken> accessToken = jwtService.getAccessToken();
        JwtToken jwtToken = accessToken.blockFirst();
        System.out.println("JwtToken: " + jwtToken);
    }
}
