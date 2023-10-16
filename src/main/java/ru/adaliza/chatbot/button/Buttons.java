package ru.adaliza.chatbot.button;

import static ru.adaliza.chatbot.command.BotCommand.*;

import lombok.experimental.UtilityClass;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@UtilityClass
public class Buttons {
    private static final InlineKeyboardButton SHOW_BUTTON =
            new InlineKeyboardButton("Show shopping list");
    private static final InlineKeyboardButton ADD_BUTTON = new InlineKeyboardButton("Add product");
    private static final InlineKeyboardButton EDIT_BUTTON =
            new InlineKeyboardButton("Edit product");
    private static final InlineKeyboardButton REMOVE_BUTTON =
            new InlineKeyboardButton("Remove product");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");
    private static final InlineKeyboardButton MAIN_MENU_BUTTON = new InlineKeyboardButton("Main menu");

    public static InlineKeyboardMarkup inlineMainMenuMarkup() {
        SHOW_BUTTON.setCallbackData(SHOW.getTextCommand());
        ADD_BUTTON.setCallbackData(ADD.getTextCommand());
        EDIT_BUTTON.setCallbackData(EDIT.getTextCommand());
        REMOVE_BUTTON.setCallbackData(REMOVE.getTextCommand());
        HELP_BUTTON.setCallbackData(HELP.getTextCommand());

        List<InlineKeyboardButton> rowInline = List.of(ADD_BUTTON, EDIT_BUTTON, REMOVE_BUTTON);
        List<List<InlineKeyboardButton>> rows =
                List.of(List.of(SHOW_BUTTON), rowInline, List.of(HELP_BUTTON));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rows);

        return markupInline;
    }

    public static InlineKeyboardMarkup inlineInnerMenuMarkup() {
        MAIN_MENU_BUTTON.setCallbackData(MENU.getTextCommand());

        List<List<InlineKeyboardButton>> rows = List.of(List.of(MAIN_MENU_BUTTON));
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rows);

        return markupInline;
    }
}
