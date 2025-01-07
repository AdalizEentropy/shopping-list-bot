package ru.adaliza.chatbot.service;

import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.adaliza.chatbot.dao.ProductRepository;
import ru.adaliza.chatbot.model.Product;
import ru.adaliza.chatbot.service.language.LanguageCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final String ERROR_CATEGORY = "Other";
    private final ProductRepository repository;
    private final WebClient webClient;
    private final UserSettingsService userSettingsService;

    @Override
    public List<Product> getAllProducts(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public void removeAllProducts(Long userId) {
        repository.removeAllByUserId(userId);
        log.debug("All products were removed for userId {}", userId);
    }

    @Override
    @Transactional
    public void removeProductById(Long productId) {
        repository.deleteById(productId);
        log.debug("Product with id {} was removed", productId);
    }

    @Override
    @Transactional
    public void addProduct(Long userId, String productName, LanguageCode languageCode) {
        boolean useCategory = userSettingsService.useCategory(userId);
        if (!useCategory) {
            addProduct(userId, productName, ERROR_CATEGORY);
            return;
        }

        try {
            webClient
                    .get()
                    .uri(
                            builder ->
                                    builder.queryParam("product", productName)
                                            .queryParam("lang", languageCode)
                                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .single()
                    .onErrorResume(
                            error -> {
                                log.warn("Ai client request error: {}", error.getMessage());
                                return Mono.just(ERROR_CATEGORY);
                            })
                    .subscribe(
                            cat -> {
                                addProduct(userId, productName, cat);
                            });
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            addProduct(userId, productName, ERROR_CATEGORY);
        }
    }

    @Override
    public int getProductQuantity(Long userId) {
        return repository.countByUserId(userId);
    }

    private String formatCategory(String category) {
        String lowerCase = category.toLowerCase(Locale.ROOT);
        return StringUtils.capitalize(lowerCase);
    }

    private void addProduct(Long userId, String productName, String cat) {
        repository.addProductByUserId(userId, productName, formatCategory(cat));
        log.debug("Product '{}' for userId {} was added", productName, userId);
    }
}
