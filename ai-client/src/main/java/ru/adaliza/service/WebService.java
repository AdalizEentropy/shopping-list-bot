package ru.adaliza.service;

import reactor.core.publisher.Flux;

public interface WebService {

    Flux<String> getProductCategory(String product);
}
