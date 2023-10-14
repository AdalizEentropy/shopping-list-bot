package ru.adaliza.chatbot.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class User {
    private final int id;
    private final String name;
    private final String description;
    private String startWord = "";

    @Override
    public String toString() { return startWord + description; }
}
