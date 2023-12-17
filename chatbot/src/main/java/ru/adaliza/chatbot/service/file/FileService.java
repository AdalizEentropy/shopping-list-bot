package ru.adaliza.chatbot.service.file;

public interface FileService<T> {

    void readFile();

    T getData();
}
