package ru.adaliza.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Choice {
    private int index;

    @JsonProperty("finish_reason")
    private String finishReason;

    private WebMessage message;
}
