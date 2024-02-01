package ru.adaliza.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import ru.adaliza.exception.WebRequestException;
import ru.adaliza.model.JwtToken;
import ru.adaliza.properties.WebClientProperties;

class JwtServiceTest {
    private static MockWebServer mockWebServer;
    private static JwtService jwtService;

    @BeforeAll
    static void setUpMockWebServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        String testUrl = mockWebServer.url("/").url().toString();
        WebClientProperties properties =
                new WebClientProperties("", testUrl, "", "", "test-scope", Duration.ofMillis(2000));
        jwtService = new JwtService(WebClient.builder().baseUrl(testUrl).build(), properties);
    }

    @AfterAll
    static void tearDownMockWebServer() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void shouldReturn_accessTokenFromMemory_ifTokenInMemory_valid() throws IllegalAccessException {
        long expDate = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli();
        var tokenInMem = new JwtToken("token", expDate);

        FieldUtils.writeField(jwtService, "jwtToken", tokenInMem, true);
        JwtToken returnedToken = jwtService.getAccessToken();

        assertEquals(tokenInMem, returnedToken);
    }

    @Test
    void shouldReturn_accessTokenFromServer_ifTokenInMemory_expired()
            throws IllegalAccessException {
        long expDate = Instant.now().minusSeconds(20).toEpochMilli();
        var tokenInMem = new JwtToken("token", expDate);
        FieldUtils.writeField(jwtService, "jwtToken", tokenInMem, true);

        String tokenFromServer = "{\"access_token\": \"token_from_server\", \"expires_at\": 12345}";
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(tokenFromServer));

        JwtToken returnedToken = jwtService.getAccessToken();
        assertThat(returnedToken)
                .hasFieldOrPropertyWithValue("accessToken", "token_from_server")
                .hasFieldOrPropertyWithValue("expiresAt", 12345L);
    }

    @Test
    void shouldReturn_accessTokenFromServer_ifNotTokenInMemory() throws IllegalAccessException {
        FieldUtils.writeField(jwtService, "jwtToken", null, true);

        String tokenFromServer = "{\"access_token\": \"token_from_server\", \"expires_at\": 12345}";
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(tokenFromServer));

        JwtToken returnedToken = jwtService.getAccessToken();
        assertThat(returnedToken)
                .hasFieldOrPropertyWithValue("accessToken", "token_from_server")
                .hasFieldOrPropertyWithValue("expiresAt", 12345L);
    }

    @Test
    void shouldGet_accessTokenFromServer_onlyOneTime() throws IllegalAccessException {
        FieldUtils.writeField(jwtService, "jwtToken", null, true);

        long expDate = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli();
        String tokenFromServer =
                "{\"access_token\": \"token_from_server\", \"expires_at\":" + expDate + "}";
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(tokenFromServer));

        JwtToken returnedToken = jwtService.getAccessToken();
        assertThat(returnedToken)
                .hasFieldOrPropertyWithValue("accessToken", "token_from_server")
                .hasFieldOrPropertyWithValue("expiresAt", expDate);

        JwtToken returnedToken2 = jwtService.getAccessToken();
        assertThat(returnedToken2)
                .hasFieldOrPropertyWithValue("accessToken", "token_from_server")
                .hasFieldOrPropertyWithValue("expiresAt", expDate);
    }

    @Test
    void shouldThrow_ifAccessTokenFromServer_returnWithErrorCode() throws IllegalAccessException {
        FieldUtils.writeField(jwtService, "jwtToken", null, true);

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        WebRequestException exception =
                assertThrows(WebRequestException.class, () -> jwtService.getAccessToken());
        assertTrue(
                exception
                        .getMessage()
                        .contains("Jwt token request error. 404 Not Found from POST"));
    }

    @Test
    void shouldThrow_ifNoResponse_fromServer() throws IllegalAccessException {
        FieldUtils.writeField(jwtService, "jwtToken", null, true);

        WebRequestException exception =
                assertThrows(WebRequestException.class, () -> jwtService.getAccessToken());
        assertEquals("Timeout on blocking read for 2000000000 NANOSECONDS", exception.getMessage());
    }
}
