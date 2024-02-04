package ru.adaliza.chatbot.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import ru.adaliza.chatbot.command.model.UpdateContext;
import ru.adaliza.chatbot.keyboard.InlineKeyboardInitializer;
import ru.adaliza.chatbot.model.Product;
import ru.adaliza.chatbot.service.ProductService;
import ru.adaliza.chatbot.service.language.model.LanguageData;

@ExtendWith(MockitoExtension.class)
class ShowCommandServiceTest {
    @Mock private ProductService productService;
    @Mock private InlineKeyboardInitializer keyboardInitializer;
    private ShowCommandService showCommandService;

    @BeforeEach
    void init() {
        showCommandService = new ShowCommandService(productService, keyboardInitializer);
    }

    @Test
    void shouldReturn_groupCategories() {
        var langData = LanguageData.builder().fullList("List").otherProduct("Other").build();
        when(productService.getAllProducts(1L))
                .thenReturn(
                        List.of(
                                new Product(1L, "prod1", "cat"),
                                new Product(2L, "prod2", "cat"),
                                new Product(3L, "prod3", null),
                                new Product(4L, "prod4", null),
                                new Product(5L, "prod5", "")));

        EditMessageText message =
                (EditMessageText)
                        showCommandService.createMessageForCommand(
                                new UpdateContext(1L, 1, "show", langData));

        assertEquals(
                """
                <b><u>List</u></b>

                <i>cat</i>
                - prod1
                - prod2

                <i>Other</i>
                - prod3
                - prod4
                - prod5
                """,
                message.getText());
    }

    @Test
    void shouldReturn_emptyList() {
        var langData = LanguageData.builder().emptyList("Empty").build();
        when(productService.getAllProducts(1L)).thenReturn(Collections.emptyList());

        EditMessageText message =
                (EditMessageText)
                        showCommandService.createMessageForCommand(
                                new UpdateContext(1L, 1, "show", langData));

        assertEquals("Empty", message.getText());
    }
}
