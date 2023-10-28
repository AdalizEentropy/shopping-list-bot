package ru.adaliza.chatbot.service;

import static ru.adaliza.chatbot.command.BotCommand.START;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.adaliza.chatbot.command.BotCommand;
import ru.adaliza.chatbot.dao.ShoppingListRepository;
import ru.adaliza.chatbot.dao.UserRepository;
import ru.adaliza.chatbot.exception.IllegalPhaseException;
import ru.adaliza.chatbot.model.ShoppingList;
import ru.adaliza.chatbot.model.User;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingListRepository shoppingListRepository;

    @Transactional
    public void addUser(Long chatId, String userName) {
        Optional<User> user = userRepository.findById(chatId);
        if (user.isEmpty()) {
            userRepository.save(new User(chatId, userName, START, true));
            shoppingListRepository.save(new ShoppingList(chatId));
        } else {
            User updatedUser = user.get();
            updatedUser.setChatPhase(START);
            userRepository.save(updatedUser);
        }
    }

    public Optional<User> getUser(Long chatId) {
        return userRepository.findById(chatId);
    }

    @Transactional
    public void updatePhase(Long chatId, BotCommand command) {
        User user = findUser(chatId);
        user.setChatPhase(command);
        userRepository.save(user);
    }

    public void updateMainMessageId(Long chatId, Integer mainMessageId) {
        User user = findUser(chatId);
        user.setMainMessageId(mainMessageId);
        userRepository.save(user);
    }

    private User findUser(Long chatId) {
        Optional<User> savedUsed = userRepository.findById(chatId);
        if (savedUsed.isPresent()) {
            return savedUsed.get();
        } else {
            log.warn("Inappropriate phase execution.");
            throw new IllegalPhaseException("Inappropriate phase execution");
        }
    }
}
