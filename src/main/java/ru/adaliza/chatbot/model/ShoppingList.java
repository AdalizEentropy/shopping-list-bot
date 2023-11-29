package ru.adaliza.chatbot.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Getter
@Table("shopping_lists")
public class ShoppingList {
    private final @Id Long id;
    private final Long userId;

    @MappedCollection(idColumn = "id")
    private final Set<Product> products;

    public ShoppingList(Long userId) {
        this(null, userId, new HashSet<>());
    }

    @PersistenceCreator
    public ShoppingList(Long id, Long userId, Set<Product> products) {
        this.id = id;
        this.userId = userId;
        this.products = products;
    }
}
