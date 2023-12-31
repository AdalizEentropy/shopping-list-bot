package ru.adaliza.chatbot.service;

import java.util.List;
import ru.adaliza.chatbot.model.Product;

public interface ProductService {

    List<Product> getAllProducts(Long userId);

    void removeAllProducts(Long userId);

    void removeProductById(Long productId);

    void addProduct(Long chatId, String productName);

    int getProductQuantity(Long userId);
}
