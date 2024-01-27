package ru.adaliza.chatbot.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.adaliza.chatbot.dao.ProductRepository;
import ru.adaliza.chatbot.exception.WebRequestException;
import ru.adaliza.chatbot.model.Product;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final String ERROR_CATEGORY = "Other";
    private final ProductRepository repository;
    private final WebClient webClient;

    @Override
    public List<Product> getAllProducts(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public void removeAllProducts(Long userId) {
        repository.removeAllByUserId(userId);
    }

    @Override
    public void removeProductById(Long productId) {
        repository.deleteById(productId);
    }

    @Override
    public void addProduct(Long userId, String productName) {
        try {
            webClient
                    .get()
                    .uri(builder -> builder.queryParam("product", productName).build())
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::isError,
                            error ->
                                    Mono.error(
                                            new WebRequestException(
                                                    "Ai client request error. Status: "
                                                            + error.statusCode())))
                    .bodyToMono(String.class)
                    .onErrorResume(
                            error -> {
                                log.warn("Ai client request error: {}", error.getMessage());
                                return Mono.just(ERROR_CATEGORY);
                            })
                    .subscribe(cat -> repository.addProductByUserId(userId, productName, cat));
        } catch (WebRequestException ex) {
            log.warn(ex.getMessage());
            repository.addProductByUserId(userId, productName, ERROR_CATEGORY);
        }
    }

    @Override
    public int getProductQuantity(Long userId) {
        return repository.countByUserId(userId);
    }
}
