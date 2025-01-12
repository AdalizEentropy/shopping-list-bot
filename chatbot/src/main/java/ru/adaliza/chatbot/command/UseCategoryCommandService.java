package ru.adaliza.chatbot.command;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.adaliza.chatbot.command.model.UpdateContext;
import ru.adaliza.chatbot.keyboard.InlineKeyboardInitializer;
import ru.adaliza.chatbot.service.UserSettingsService;
import ru.adaliza.chatbot.service.language.model.LanguageData;

@Service("useCategoryCommand")
@RequiredArgsConstructor
public class UseCategoryCommandService extends AbstractCommandService {
    private final InlineKeyboardInitializer keyboardInitializer;
    private final UserSettingsService userSettingsService;
    private LanguageData languageData;
    private Boolean alreadyUseCategory;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        languageData = updateContext.languageData();
        String text;
        if (!updateContext.command().equals(BotCommand.CATEGORY.getCommand())) {
            userSettingsService.changeUseCategory(
                    updateContext.chatId(),
                    updateContext.command().equals(BotCommand.ENABLE.getCommand()));
            alreadyUseCategory = null;
            text = languageData.settingsChanged();
        } else {
            alreadyUseCategory = userSettingsService.useCategory(updateContext.chatId());
            text = languageData.settings();
        }

        return createKeyboardReplyMessage(updateContext, text);
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        if (alreadyUseCategory == null) {
            return keyboardInitializer.inlineInnerMenuMarkup(languageData);
        } else {
            return keyboardInitializer.inlineEnableOptionMarkup(languageData, !alreadyUseCategory);
        }
    }
}
