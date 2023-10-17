package ru.adaliza.chatbot.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.adaliza.chatbot.dao.ShoppingListRepository;
import ru.adaliza.chatbot.dao.UserRepository;
import ru.adaliza.chatbot.model.ShoppingList;
import ru.adaliza.chatbot.model.User;

@Service
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
}
