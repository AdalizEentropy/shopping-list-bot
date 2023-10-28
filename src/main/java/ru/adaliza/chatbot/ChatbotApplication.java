package ru.adaliza.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatbotApplication {

    // TODO пофиксить o.s.d.j.c.c.ResultSetAccessor - 240083736: ResultSet contains id multiple
    // times

    // TODO пофиксить Error executing
    // org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText query: [400] Bad
    // Request: message is not modified: specified new message content and reply markup are exactly
    // the same as a current content and reply markup of the message
    public static void main(String[] args) {
        SpringApplication.run(ChatbotApplication.class, args);
    }
}
