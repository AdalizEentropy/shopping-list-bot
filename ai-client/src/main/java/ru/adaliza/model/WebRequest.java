package ru.adaliza.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * "model": идентификатор модели, можно указать конкретную или :latest
 *
 * <p>"temperature": от 0 до 2, чем выше, тем вывод более случайный
 *
 * <p>"n": от 1 до 4, число вариантов ответов модели
 *
 * <p>"max_tokens": максимальное число токенов для генерации ответов
 *
 * <p>"repetition_penalty": количество повторений слов, 1.0 - ни чего не менять, от 0 до 1 повторять
 * уже сказанные слова, от 1 и далее не использовать сказанные слова
 *
 * <p>"stream": если true, будут отправляться частичные ответы сообщений
 *
 * <p>"update_interval": интервал в секундах, не чаще которого будут присылаться токены в stream
 * режиме
 *
 * <p>"messages": [ { "role": "system", // контекст "content": "Отвечай как научный сотрудник" }, {
 * "role": "user", // запрос пользователя "content": "Напиши 5 вариантов названий для космической
 * станции" } ]
 */
@Getter
@Setter
@ToString
public class WebRequest {
    private final String model;
    private final double temperature;
    private final int n;
    private final boolean stream;
    private final List<WebMessage> messages;

    @JsonProperty("max_tokens")
    private final int maxTokens;

    @JsonProperty("repetition_penalty")
    private final double repetitionPenalty;

    @JsonProperty("update_interval")
    private final int updateInterval;

    public WebRequest(List<WebMessage> messages) {
        this.model = "GigaChat:latest";
        this.temperature = 0.87;
        this.n = 1;
        this.maxTokens = 512;
        this.repetitionPenalty = 1.0;
        this.stream = false;
        this.updateInterval = 0;
        this.messages = messages;
    }
}
