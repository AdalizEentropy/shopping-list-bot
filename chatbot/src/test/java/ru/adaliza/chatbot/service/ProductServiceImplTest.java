package ru.adaliza.chatbot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import ru.adaliza.chatbot.dao.ProductRepository;
import ru.adaliza.chatbot.model.Product;
import ru.adaliza.chatbot.service.language.LanguageCode;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    private static MockWebServer mockWebServer;
    private static String testUrl;
    @Mock private ProductRepository repository;
    private ProductService productService;

    @BeforeAll
    static void setUpMockWebServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        testUrl = mockWebServer.url("/").url().toString();
    }

    @AfterAll
    static void tearDownMockWebServer() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void initialize() {
        productService =
                new ProductServiceImpl(repository, WebClient.builder().baseUrl(testUrl).build());
    }

    @Test
    void shouldReturn_allProducts() {
        Product product1InDB = new Product(1L, "prod1", "cat1");
        Product product2InDB = new Product(2L, "prod2", "cat2");

        when(repository.findAllByUserId(1)).thenReturn(List.of(product1InDB, product2InDB));
        List<Product> allProducts = productService.getAllProducts(1L);

        assertThat(allProducts).isNotEmpty().hasSize(2);
        assertThat(allProducts.get(0)).usingRecursiveComparison().isEqualTo(product1InDB);
    }

    @Test
    void shouldRemove_allProducts() {
        productService.removeAllProducts(1L);

        verify(repository, times(1)).removeAllByUserId(1L);
    }

    @Test
    void shouldRemove_byId() {
        productService.removeProductById(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void shouldAddProduct_withCategory_ifRespOk() {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("vegetable"));
        productService.addProduct(1L, "tomato", LanguageCode.EN);

        await().untilAsserted(
                        () -> verify(repository).addProductByUserId(1L, "tomato", "Vegetable"));
    }

    @Test
    void shouldAddProduct_withDefaultCategory_ifRespError() {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("vegetable"));
        productService.addProduct(1L, "tomato", LanguageCode.EN);

        await().untilAsserted(() -> verify(repository).addProductByUserId(1L, "tomato", "Other"));
    }

    @Test
    void shouldAddProduct_withDefaultCategory_ifNoResp() {
        mockWebServer.enqueue(new MockResponse().setBodyDelay(1, TimeUnit.SECONDS));
        productService.addProduct(1L, "tomato", LanguageCode.EN);

        await().untilAsserted(() -> verify(repository).addProductByUserId(1L, "tomato", "Other"));
    }

    @Test
    void shouldReturn_productQuantity() {
        when(repository.countByUserId(1)).thenReturn(5);
        int productQuantity = productService.getProductQuantity(1L);

        assertThat(productQuantity).isEqualTo(5);
    }
}
