package br.com.project.webfluxdemo;

import br.com.project.webfluxdemo.dto.MultiplyRequest;
import br.com.project.webfluxdemo.dto.Response;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec04HeadersTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void headerRequest() {
        var response = webClient
                .get()
                .uri("calculator/{firs}/{second}", 5, 10)
                .headers(h -> h.set("operation", "+"))
                .retrieve()
                .bodyToMono(Integer.class);

        StepVerifier.create(response)
                .expectNextMatches(r -> r == 15)
                .verifyComplete();
    }
}
