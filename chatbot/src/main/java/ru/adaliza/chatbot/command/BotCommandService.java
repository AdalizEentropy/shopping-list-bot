package ru.adaliza.chatbot.command;

import java.io.Serializable;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.adaliza.chatbot.command.model.UpdateContext;

public interface BotCommandService {

    BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext);
}
