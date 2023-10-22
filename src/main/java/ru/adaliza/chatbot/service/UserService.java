package ru.adaliza.chatbot.service;

import ru.adaliza.chatbot.command.BotCommand;
import ru.adaliza.chatbot.model.User;

import java.util.Optional;

public interface UserService {

    void addUser(Long chatId, String userName);

    Optional<User> getUser(Long chatId);

    void updatePhase(Long chatId, BotCommand command);
}
