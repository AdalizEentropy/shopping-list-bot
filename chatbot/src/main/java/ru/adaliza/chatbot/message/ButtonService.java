package ru.adaliza.chatbot.message;

import java.io.Serializable;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.adaliza.chatbot.command.BotCommand;
import ru.adaliza.chatbot.command.ButtonCommands;
import ru.adaliza.chatbot.command.model.UpdateContext;
import ru.adaliza.chatbot.model.User;
import ru.adaliza.chatbot.service.UserService;
import ru.adaliza.chatbot.service.language.LanguageConverter;
import ru.adaliza.chatbot.service.language.model.LanguageData;

@Service
@RequiredArgsConstructor
public class ButtonService implements MessageService<Serializable> {
    private final ButtonCommands buttonCommands;
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
        } else if (botCommand == BotCommand.ENABLE || botCommand == BotCommand.DISABLE) {
            return executeEnableOptionButton(updateContext);
        } else {
            userService.updatePhase(chatId, botCommand);
        }

        return buttonCommands.getCommand(botCommand).createMessageForCommand(updateContext);
    }

    private BotApiMethod<Serializable> executeMayBeProductButton(UpdateContext updateContext) {
        Optional<User> user = userService.getUser(updateContext.chatId());
        if (user.isPresent() && user.get().getChatPhase() == BotCommand.REMOVE) {
            return buttonCommands
                    .getCommand(BotCommand.REMOVE)
                    .createMessageForCommand(updateContext);
        } else {
            return buttonCommands
                    .getCommand(BotCommand.UNKNOWN)
                    .createMessageForCommand(updateContext);
        }
    }

    private BotApiMethod<Serializable> executeEnableOptionButton(UpdateContext updateContext) {
        Optional<User> user = userService.getUser(updateContext.chatId());
        if (user.isPresent() && user.get().getChatPhase() == BotCommand.CATEGORY) {
            return buttonCommands
                    .getCommand(BotCommand.CATEGORY)
                    .createMessageForCommand(updateContext);
        } else {
            return buttonCommands
                    .getCommand(BotCommand.UNKNOWN)
                    .createMessageForCommand(updateContext);
        }
    }
}
