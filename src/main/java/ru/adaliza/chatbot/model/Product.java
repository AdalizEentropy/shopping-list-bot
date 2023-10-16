package ru.adaliza.chatbot.model;

public record Product(Long id, String name, Long shoppingListId) {

    @Override
    public String toString() {
        return name;
    }
}
