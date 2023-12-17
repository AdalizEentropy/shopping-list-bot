package ru.adaliza.chatbot.dao;

import org.springframework.data.repository.CrudRepository;
import ru.adaliza.chatbot.model.ShoppingList;

public interface ShoppingListRepository extends CrudRepository<ShoppingList, Long> {}
