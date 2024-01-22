package ru.adaliza.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WebMessage {
    private WebMessageRole role;
    private String content;
}
