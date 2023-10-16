package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import ru.adaliza.chatbot.dao.InMemoryRepository;
import ru.adaliza.chatbot.model.Product;

import java.util.List;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.*;

@Service
@RequiredArgsConstructor
public class CommandService implements BotCommandService {
    private final InMemoryRepository repository;

    @Override
    public SendMessage replyOnCommand(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String command = update.getCallbackQuery().getData();
        BotCommand botCommand = BotCommand.valueOfCommand(command);

        SendMessage replyMessage;
        switch (botCommand) {
            case ADD -> replyMessage = addCommand(chatId);
            case EDIT -> replyMessage = editCommand(chatId);
            case REMOVE -> replyMessage = removeCommand(chatId);
            case SHOW -> replyMessage = showCommand(chatId);
            case HELP -> replyMessage = helpCommand(chatId);
            case START -> replyMessage = startCommand(chatId);
            default -> replyMessage = unknownCommand(chatId);
        }

        return replyMessage;
    }

    private SendMessage startCommand(Long chatId) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, "choose command");
        sendMessage.setReplyMarkup(Buttons.inlineMainMenuMarkup());

        return sendMessage;
    }

    private SendMessage addCommand(Long chatId) {
        var text = "Enter product name for adding.";
        return createTextReplyMessage(chatId, text);
    }

    private SendMessage editCommand(Long chatId) {
        var text = "Enter product for edit.";
        return createTextReplyMessage(chatId, text);
    }

    private SendMessage removeCommand(Long chatId) {
        var text = "Enter product for remove.";
        return createTextReplyMessage(chatId, text);
    }

    private SendMessage showCommand(Long chatId) {
        List<Product> allShoppingList = repository.getAllShoppingList();
        StringBuilder builder = new StringBuilder();
        builder.append("*Your shopping list:*");
        builder.append("\n");
        allShoppingList.forEach(
                product -> {
                    builder.append("\\- ");
                    builder.append(product.name());
                    builder.append("\n");
                });
        return createTextReplyMessage(chatId, builder.toString());
    }

    private SendMessage helpCommand(Long chatId) {
        var text = "Here will be help";
        return createTextReplyMessage(chatId, text);
    }

    private SendMessage unknownCommand(Long chatId) {
        var text = "Unknown command\\!";
        return createTextReplyMessage(chatId, text);
    }

    private SendMessage createTextReplyMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        sendMessage.setReplyMarkup(Buttons.inlineInnerMenuMarkup());
        sendMessage.setParseMode(MARKDOWNV2);

        return sendMessage;
    }
}
