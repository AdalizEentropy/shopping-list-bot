package ru.adaliza.chatbot.service.language.model;

import lombok.Builder;

@Builder
public record ButtonSettingsNames(String name, String useCategory, String enable, String disable) {}
