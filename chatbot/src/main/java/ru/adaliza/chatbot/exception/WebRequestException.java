package ru.adaliza.chatbot.exception;

public class WebRequestException extends RuntimeException {

    public WebRequestException(String message) {
        super(message);
    }
}
