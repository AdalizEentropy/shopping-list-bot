package ru.adaliza.chatbot.keyboard;

import static ru.adaliza.chatbot.command.BotCommand.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.adaliza.chatbot.model.Product;
import ru.adaliza.chatbot.service.language.model.LanguageData;

@Component
public class InlineKeyboardInitializer {
    private final InlineKeyboardButton showButton;
    private final InlineKeyboardButton addButton;
    private final InlineKeyboardButton removeButton;
    private final InlineKeyboardButton clearButton;
    private final InlineKeyboardButton settingsButton;
    private final InlineKeyboardButton helpButton;
    private final InlineKeyboardButton mainMenuButton;
    private final InlineKeyboardButton useCategoryButton;
    private final InlineKeyboardButton enableButton;
    private final InlineKeyboardButton disableButton;

    public InlineKeyboardInitializer() {
        showButton = new InlineKeyboardButton();
        addButton = new InlineKeyboardButton();
        removeButton = new InlineKeyboardButton();
        clearButton = new InlineKeyboardButton();
        helpButton = new InlineKeyboardButton();
        mainMenuButton = new InlineKeyboardButton();
        settingsButton = new InlineKeyboardButton();
        useCategoryButton = new InlineKeyboardButton();
        enableButton = new InlineKeyboardButton();
        disableButton = new InlineKeyboardButton();
        initInlineKeyboardButtonCallbackData();
    }

    public InlineKeyboardMarkup inlineMainMenuMarkup(LanguageData languageData) {
        initInlineKeyboardButtonNames(languageData);
        List<InlineKeyboardButton> rowInline = List.of(addButton, removeButton, clearButton);
        List<List<InlineKeyboardButton>> rows =
                List.of(
                        List.of(showButton),
                        rowInline,
                        List.of(settingsButton),
                        List.of(helpButton));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rows);

        return markupInline;
    }

    public InlineKeyboardMarkup inlineInnerMenuMarkup(LanguageData languageData) {
        initInlineKeyboardButtonNames(languageData);
        List<List<InlineKeyboardButton>> rows = List.of(List.of(mainMenuButton));
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rows);

        return markupInline;
    }

    public InlineKeyboardMarkup inlineProductsMarkup(List<Product> products) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (Product product : products) {
            InlineKeyboardButton productButton = new InlineKeyboardButton(product.name());
            productButton.setCallbackData(String.valueOf(product.id()));
            rows.add(List.of(productButton));
        }

        rows.add(List.of(mainMenuButton));
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rows);

        return markupInline;
    }

    public InlineKeyboardMarkup inlineSettingsMarkup(LanguageData languageData) {
        initInlineKeyboardButtonNames(languageData);
        List<List<InlineKeyboardButton>> rows =
                List.of(List.of(useCategoryButton), List.of(mainMenuButton));
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rows);

        return markupInline;
    }

    public InlineKeyboardMarkup inlineEnableOptionMarkup(
            LanguageData languageData, Boolean enable) {
        initInlineKeyboardButtonNames(languageData);
        List<List<InlineKeyboardButton>> rows =
                List.of(List.of(enable ? enableButton : disableButton), List.of(mainMenuButton));
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rows);

        return markupInline;
    }

    public void initInlineKeyboardButtonNames(LanguageData languageData) {
        showButton.setText("🛒 " + languageData.buttons().show());
        addButton.setText("➕ " + languageData.buttons().add());
        removeButton.setText("➖ " + languageData.buttons().remove());
        clearButton.setText("❌ " + languageData.buttons().clear());
        settingsButton.setText("⚙\uFE0F " + languageData.buttons().settings().name());
        helpButton.setText("❔ " + languageData.buttons().help());
        mainMenuButton.setText("🔙 " + languageData.buttons().menu());
        useCategoryButton.setText(languageData.buttons().settings().useCategory());
        enableButton.setText(languageData.buttons().settings().enable());
        disableButton.setText(languageData.buttons().settings().disable());
    }

    private void initInlineKeyboardButtonCallbackData() {
        showButton.setCallbackData(SHOW.getCommand());
        addButton.setCallbackData(ADD.getCommand());
        clearButton.setCallbackData(CLEAR.getCommand());
        removeButton.setCallbackData(REMOVE.getCommand());
        settingsButton.setCallbackData(SETTINGS.getCommand());
        helpButton.setCallbackData(HELP.getCommand());
        mainMenuButton.setCallbackData(MENU.getCommand());
        useCategoryButton.setCallbackData(CATEGORY.getCommand());
        enableButton.setCallbackData(ENABLE.getCommand());
        disableButton.setCallbackData(DISABLE.getCommand());
    }
}
