package ru.adaliza.module;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WebMessage {
    private WebMessageRole role;
    private String content;
}
