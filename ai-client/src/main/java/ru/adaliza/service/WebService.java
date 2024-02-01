package ru.adaliza.service;

import reactor.core.publisher.Mono;

public interface WebService {

    Mono<String> getProductCategory(String product, String lang);
}
