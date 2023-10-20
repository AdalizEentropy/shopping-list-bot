package ru.adaliza.chatbot.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.adaliza.chatbot.command.BotCommand;
import ru.adaliza.chatbot.dao.ShoppingListRepository;
import ru.adaliza.chatbot.dao.UserRepository;
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
        var user = userRepository.findById(chatId);
        if (user.isEmpty()) {
            userRepository.save(new User(chatId, userName, true));
            shoppingListRepository.save(new ShoppingList(chatId));
        }
    }

    public Optional<User> getUser(Long chatId) {
        return userRepository.findById(chatId);
    }

    @Transactional
    public boolean updatePhase(Long chatId, BotCommand command) {
        Optional<User> savedUsed = userRepository.findById(chatId);
        if (savedUsed.isPresent()) {
            User user = savedUsed.get();
            user.setChatPhase(command.toString());
            userRepository.save(user);
            return true;
        } else {
            log.warn("Inappropriate phase execution. Phase: {}", command);
            return false;
        }
    }
}
