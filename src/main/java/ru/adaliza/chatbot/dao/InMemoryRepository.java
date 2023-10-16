package ru.adaliza.chatbot.dao;

import org.springframework.stereotype.Component;
import ru.adaliza.chatbot.model.Product;

import java.util.List;

@Component
// TODO replace to DB
public class InMemoryRepository {

    public List<Product> getAllShoppingList() {
        return List.of(
                new Product(1L, "tomato", 1L),
                new Product(2L, "cucumber 1kg", 1L),
                new Product(3L, "eggs 10", 1L));
    }
}
