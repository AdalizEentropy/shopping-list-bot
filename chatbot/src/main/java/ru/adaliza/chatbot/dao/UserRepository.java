package ru.adaliza.chatbot.dao;

import org.springframework.data.repository.CrudRepository;
import ru.adaliza.chatbot.model.User;

public interface UserRepository extends CrudRepository<User, Long> {}
