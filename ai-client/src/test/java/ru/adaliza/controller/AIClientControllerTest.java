package ru.adaliza.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;
import ru.adaliza.service.WebService;

@WebMvcTest(controllers = AIClientController.class)
class AIClientControllerTest {
    private static final String URL = "/ai-client/category";
    @Autowired private MockMvc mvc;
    @MockBean private WebService webService;

    @Test
    void shouldGet_productCategory() throws Exception {
        when(webService.getProductCategory("test_product", "EN"))
                .thenReturn(Mono.just("test_category"));

        Object asyncResult =
                mvc.perform(get(URL).queryParam("product", "test_product"))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getAsyncResult();

        assertEquals("test_category", asyncResult);
    }

    @Test
    void shouldGet_productCategory_withSpecificLang() throws Exception {
        when(webService.getProductCategory("test_product", "RU"))
                .thenReturn(Mono.just("тест_категория"));

        Object asyncResult =
                mvc.perform(get(URL).queryParam("product", "test_product").queryParam("lang", "RU"))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getAsyncResult();

        assertEquals("тест_категория", asyncResult);
    }

    @Test
    void shouldError_ifProduct_notExist() throws Exception {
        when(webService.getProductCategory(any(), any())).thenReturn(Mono.just("test_product"));

        mvc.perform(get(URL)).andExpect(status().is4xxClientError());
    }
}
