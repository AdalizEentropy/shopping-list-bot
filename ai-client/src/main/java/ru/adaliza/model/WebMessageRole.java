package ru.adaliza.model;

public enum WebMessageRole {
    SYSTEM("system"),
    USER("user");

    private final String role;

    WebMessageRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
