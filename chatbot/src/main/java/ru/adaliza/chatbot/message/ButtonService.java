package ru.adaliza.chatbot.message;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.adaliza.chatbot.command.BotCommand;
import ru.adaliza.chatbot.command.BotCommandService;
import ru.adaliza.chatbot.command.model.UpdateContext;
import ru.adaliza.chatbot.model.User;
import ru.adaliza.chatbot.service.UserService;
import ru.adaliza.chatbot.service.language.LanguageConverter;
import ru.adaliza.chatbot.service.language.model.LanguageData;

@Service
@RequiredArgsConstructor
public class ButtonService implements MessageService<Serializable> {
    private final Map<String, BotCommandService> commands;
    private final UserService userService;
    private final LanguageConverter languageConverter;

    @Override
    public BotApiMethod<Serializable> replyOnMessage(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String command = update.getCallbackQuery().getData();
        LanguageData languageData =
                languageConverter.getLanguageData(update.getCallbackQuery().getFrom());
        BotCommand botCommand = BotCommand.valueOfCommand(command);
        UpdateContext updateContext = new UpdateContext(chatId, messageId, command, languageData);

        if (botCommand == BotCommand.UNKNOWN) {
            return executeMayBeProductButton(updateContext);
        } else {
            userService.updatePhase(chatId, botCommand);
        }

        BotApiMethod<Serializable> replyMessage;
        switch (botCommand) {
            case ADD -> replyMessage =
                    commands.get("addCommand").createMessageForCommand(updateContext);
            case CLEAR -> replyMessage =
                    commands.get("clearCommand").createMessageForCommand(updateContext);
            case REMOVE -> replyMessage =
                    commands.get("removeCommand").createMessageForCommand(updateContext);
            case SHOW -> replyMessage =
                    commands.get("showCommand").createMessageForCommand(updateContext);
            case HELP -> replyMessage =
                    commands.get("helpCommand").createMessageForCommand(updateContext);
            case MENU -> replyMessage =
                    commands.get("menuCommand").createMessageForCommand(updateContext);
            default -> replyMessage =
                    commands.get("unknownCommand").createMessageForCommand(updateContext);
        }

        return replyMessage;
    }

    private BotApiMethod<Serializable> executeMayBeProductButton(UpdateContext updateContext) {
        Optional<User> user = userService.getUser(updateContext.chatId());
        if (user.isPresent() && user.get().getChatPhase() == BotCommand.REMOVE) {
            return commands.get("removeCommand").createMessageForCommand(updateContext);
        } else {
            return commands.get("unknownCommand").createMessageForCommand(updateContext);
        }
    }
}
