package ru.adaliza.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
class BaseWebServiceTest {
    @Autowired WebService webService;

    @Test
    void test() {
        Flux<String> tomat = webService.getProductCategory("tomat");
        String s = tomat.blockFirst();
        System.out.println(s);
    }
}
