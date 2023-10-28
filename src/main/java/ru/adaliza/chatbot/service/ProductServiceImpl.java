package ru.adaliza.chatbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.adaliza.chatbot.dao.ProductRepository;
import ru.adaliza.chatbot.model.Product;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

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
        repository.addProductByUserId(userId, productName);
    }
}
