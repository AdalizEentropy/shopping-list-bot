package ru.adaliza.chatbot.command;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.adaliza.chatbot.command.model.UpdateContext;

import java.io.Serializable;

public interface BotCommandService {

    BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext);
}
