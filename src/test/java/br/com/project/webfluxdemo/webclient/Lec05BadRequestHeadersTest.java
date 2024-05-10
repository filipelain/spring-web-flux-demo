package br.com.project.webfluxdemo.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

public class Lec05BadRequestHeadersTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void badRequest() {
        var response = webClient
                .get()
                .uri("calculator/{firs}/{second}", 5, 10)
                .headers(h -> h.set("operation", "^"))
                .retrieve()
                .bodyToMono(Integer.class);

        StepVerifier.create(response)
                .expectError(BadRequest.class)
                .verify();

        StepVerifier.create(response)
                .verifyError(WebClientResponseException.BadRequest.class);
    }
}
