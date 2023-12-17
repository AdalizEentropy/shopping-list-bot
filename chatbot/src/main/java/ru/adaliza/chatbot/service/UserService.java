package ru.adaliza.chatbot.service;

import java.util.Optional;
import ru.adaliza.chatbot.command.BotCommand;
import ru.adaliza.chatbot.model.User;

public interface UserService {

    void addUser(Long chatId, String userName);

    Optional<User> getUser(Long chatId);

    void updatePhase(Long chatId, BotCommand command);

    void updateMainMessageId(Long chatId, Integer mainMessageId);
}
