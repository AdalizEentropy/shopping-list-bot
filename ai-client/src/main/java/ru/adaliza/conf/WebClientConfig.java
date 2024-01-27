package ru.adaliza.conf;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.adaliza.properties.WebClientProperties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(WebClientProperties.class)
public class WebClientConfig {
    private final WebClientProperties webClientProperties;

    @Bean("jwtWebClient")
    public WebClient jwtWebClient() throws SSLException {
        String authData =
                webClientProperties.getClientId() + ":" + webClientProperties.getClientSecret();
        String authHeader =
                "Bearer "
                        + Base64.getEncoder()
                                .encodeToString(authData.getBytes(Charset.defaultCharset()));
        return WebClient.builder()
                .baseUrl(webClientProperties.getTokenUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, authHeader)
                .defaultHeader(HttpHeaders.ACCEPT, "*/*")
                .defaultHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br")
                .defaultHeader(HttpHeaders.CONNECTION, "keep-alive")
                .clientConnector(new ReactorClientHttpConnector(createClient()))
                .build();
    }

    @Bean("baseWebClient")
    public WebClient baseWebClient() throws SSLException {
        return WebClient.builder()
                .baseUrl(webClientProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, "*/*")
                .defaultHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br")
                .defaultHeader(HttpHeaders.CONNECTION, "keep-alive")
                .clientConnector(new ReactorClientHttpConnector(createClient()))
                .build();
    }

    private HttpClient createClient() throws SSLException {
        var sslContext =
                SslContextBuilder.forClient()
                        .trustManager(InsecureTrustManagerFactory.INSTANCE)
                        .build();
        return HttpClient.create()
                .secure(t -> t.sslContext(sslContext))
                .doOnConnected(
                        connection ->
                                connection.addHandlerLast(
                                        new ReadTimeoutHandler(
                                                webClientProperties
                                                        .getResponseTimeout()
                                                        .toSeconds(),
                                                TimeUnit.SECONDS)));
    }
}
