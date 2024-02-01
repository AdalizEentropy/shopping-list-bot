package ru.adaliza.service;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.adaliza.model.JwtToken;

@ExtendWith(MockitoExtension.class)
class BaseWebServiceTest {
    private static MockWebServer mockWebServer;
    private static String testUrl;
    @Mock private JwtService jwtService;
    private BaseWebService baseWebService;

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
        baseWebService =
                new BaseWebService(WebClient.builder().baseUrl(testUrl).build(), jwtService);
    }

    @Test
    void shouldReturn_productCategory_ifServerOk() {
        when(jwtService.getAccessToken()).thenReturn(new JwtToken("token", 123456));

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(
                                "{\"choices\": [{\"message\": {\"role\": \"assistant\", \"content\": \"category_test\"}}]}"));
        Mono<String> category = baseWebService.getProductCategory("tomato", "RU");

        StepVerifier.create(category).expectNext("category_test").expectComplete().verify();
    }

    @Test
    void shouldReturn_defaultProductCategory_ifServerRespErrorCode() {
        when(jwtService.getAccessToken()).thenReturn(new JwtToken("token", 123456));

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(500)
                        .setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
        Mono<String> category = baseWebService.getProductCategory("tomato", "EN");

        StepVerifier.create(category).expectNext("Other").expectComplete().verify();
    }

    @Test
    void shouldReturn_defaultProductCategory_ifServerRespNull() {
        when(jwtService.getAccessToken()).thenReturn(new JwtToken("token", 123456));

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
        Mono<String> category = baseWebService.getProductCategory("tomato", "EN");

        StepVerifier.create(category).expectNext("Other").expectComplete().verify();
    }

    @Test
    void shouldReturn_defaultProductCategory_ifServerRespTimeout() {
        when(jwtService.getAccessToken()).thenReturn(new JwtToken("token", 123456));

        mockWebServer.enqueue(new MockResponse().setBodyDelay(1, TimeUnit.SECONDS));
        Mono<String> category = baseWebService.getProductCategory("tomato", "EN");

        StepVerifier.create(category).expectNext("Other").expectComplete().verify();
    }

    @Test
    void shouldReturn_defaultProductCategory_ifServerRespIncorrect() {
        when(jwtService.getAccessToken()).thenReturn(new JwtToken("token", 123456));

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"choices\": [{\"message\": null}]}"));

        Mono<String> category = baseWebService.getProductCategory("tomato", "EN");

        StepVerifier.create(category).expectNext("Other").expectComplete().verify();
    }
}
