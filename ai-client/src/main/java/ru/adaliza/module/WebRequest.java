package ru.adaliza.module;

import java.util.List;

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
public class WebRequest {
    private final String model = "GigaChat:latest";
    private final double temperature = 0.87;
    private final int n = 1;
    private final int maxTokens = 512;
    private final double repetitionPenalty = 1.0;
    private final boolean stream = false;
    private final int updateInterval = 0;
    private List<WebMessage> messages;

    public WebRequest(List<WebMessage> messages) {
        this.messages = messages;
    }
}
