package ru.adaliza.chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.adaliza.chatbot.model.User;
import ru.adaliza.chatbot.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users_idea")
    public List<User> getIdeaList() {
        return userService.getUserList();
    }
}
