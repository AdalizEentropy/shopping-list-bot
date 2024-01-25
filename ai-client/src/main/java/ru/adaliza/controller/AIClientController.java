package ru.adaliza.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.adaliza.service.WebService;

@RestController
@RequestMapping(path = "/ai-client")
@RequiredArgsConstructor
public class AIClientController {
    private final WebService webService;

    @GetMapping("/category/{product}")
    public Mono<String> getProductCategory(@PathVariable String product) {

        return webService.getProductCategory(product);
    }
}
