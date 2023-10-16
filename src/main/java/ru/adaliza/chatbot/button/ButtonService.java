package ru.adaliza.chatbot.button;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.adaliza.chatbot.command.BotCommand;
import ru.adaliza.chatbot.command.BotCommandService;

@Service
public class ButtonService implements BotButtonService {
    private final BotCommandService addCommand;
    private final BotCommandService showCommand;
    private final BotCommandService editCommand;
    private final BotCommandService removeCommand;
    private final BotCommandService helpCommand;
    private final BotCommandService unknownCommand;
    private final BotCommandService menuCommand;

    @Autowired
    public ButtonService(
            @Qualifier("addCommand") BotCommandService addCommand,
            @Qualifier("showCommand") BotCommandService showCommand,
            @Qualifier("editCommand") BotCommandService editCommand,
            @Qualifier("removeCommand") BotCommandService removeCommand,
            @Qualifier("unknownCommand") BotCommandService unknownCommand,
            @Qualifier("menuCommand") BotCommandService menuCommand,
            @Qualifier("helpCommand") BotCommandService helpCommand) {
        this.addCommand = addCommand;
        this.showCommand = showCommand;
        this.editCommand = editCommand;
        this.removeCommand = removeCommand;
        this.unknownCommand = unknownCommand;
        this.menuCommand = menuCommand;
        this.helpCommand = helpCommand;
    }

    @Override
    public SendMessage replyOnCommand(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String command = update.getCallbackQuery().getData();
        BotCommand botCommand = BotCommand.valueOfCommand(command);

        SendMessage replyMessage;
        switch (botCommand) {
            case ADD -> replyMessage = addCommand.createMessageForCommand(chatId);
            case EDIT -> replyMessage = editCommand.createMessageForCommand(chatId);
            case REMOVE -> replyMessage = removeCommand.createMessageForCommand(chatId);
            case SHOW -> replyMessage = showCommand.createMessageForCommand(chatId);
            case HELP -> replyMessage = helpCommand.createMessageForCommand(chatId);
            case MENU -> replyMessage = menuCommand.createMessageForCommand(chatId);
            default -> replyMessage = unknownCommand.createMessageForCommand(chatId);
        }

        return replyMessage;
    }
}
