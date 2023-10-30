package ru.adaliza.chatbot.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.Buttons;

import java.io.Serializable;

@Service("helpCommand")
public class HelpCommandService extends AbstractCommandService {

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(ButtonData buttonData) {
        StringBuilder builder = new StringBuilder();
        builder.append("<b>Menu description:</b>");
        builder.append("\n");
        builder.append("🔹 <b>Show shopping list</b> - just show list of all your products.");
        builder.append("\n");
        builder.append(
                "🔹 <b>Add product</b> - enter product name and quantity for adding it to your shopping list. ");
        builder.append("You can enter products one by one without returning to main menu.");
        builder.append("\n");
        builder.append(
                "🔹 <b>Remove product</b> - select which product you wan to remove from the shopping list. ");
        builder.append("You can remove products one by one without returning to main menu.");
        builder.append("\n");
        builder.append("🔹 <b>Remove all</b> - remove all products from the shopping list.");

        return createKeyboardReplyMessage(buttonData, builder.toString());
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return Buttons.inlineInnerMenuMarkup();
    }
}
