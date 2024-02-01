package ru.adaliza.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.adaliza.service.WebService;

@RestController
@RequestMapping(path = "/ai-client")
@RequiredArgsConstructor
public class AIClientController {
    private final WebService webService;

    @GetMapping("/category")
    public Mono<String> getProductCategory(
            @RequestParam String product, @RequestParam(defaultValue = "EN") String lang) {

        return webService.getProductCategory(product, lang);
    }
}
