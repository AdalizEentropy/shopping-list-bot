package ru.adaliza.chatbot.service;

import java.util.List;
import ru.adaliza.chatbot.model.Product;
import ru.adaliza.chatbot.service.language.LanguageCode;

public interface ProductService {

    List<Product> getAllProducts(Long userId);

    void removeAllProducts(Long userId);

    void removeProductById(Long productId);

    void addProduct(Long chatId, String productName, LanguageCode languageCode);

    int getProductQuantity(Long userId);
}
