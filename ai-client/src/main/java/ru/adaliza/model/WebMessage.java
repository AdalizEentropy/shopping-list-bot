package ru.adaliza.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebMessage {
    private WebMessageRole role;
    private String content;
}
