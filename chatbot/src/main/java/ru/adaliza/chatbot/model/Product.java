package ru.adaliza.chatbot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("products")
public record Product(@Id Long id, @Column(value = "product_name") String name, String category) {}
