package ru.adaliza.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Arrays;
import java.util.Objects;
import ru.adaliza.conf.EnumJsonDeserializer;
import ru.adaliza.conf.EnumJsonSerializer;

@JsonSerialize(using = EnumJsonSerializer.class)
@JsonDeserialize(using = EnumJsonDeserializer.class)
public enum WebMessageRole {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");

    private final String role;

    WebMessageRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static WebMessageRole of(String value) {
        return Arrays.stream(values())
                .filter(v -> Objects.equals(value, v.role))
                .findFirst()
                .orElse(null);
    }
}
