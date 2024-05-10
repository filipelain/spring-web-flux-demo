package br.com.project.webfluxdemo.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void badRequest() {
        var response = webClient
                .get()
                .uri("calculator/{firs}/{second}", 5, 10)
                .headers(h -> h.set("operation", "^"))
                .exchangeToMono(this::exchange);

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse response) {
        return response.statusCode().equals(HttpStatus.BAD_REQUEST) ?
                response.bodyToMono(String.class) :
                response.bodyToMono(Integer.class);

    }
}
