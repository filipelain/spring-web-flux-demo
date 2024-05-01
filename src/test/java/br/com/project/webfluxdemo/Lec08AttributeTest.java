package br.com.project.webfluxdemo;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.test.StepVerifier;

public class Lec08AttributeTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void headerRequestBasic() {
        var response = webClient
                .get()
                .uri("calculator/{firs}/{second}", 5, 10)
                .headers(h -> h.set("operation", "+"))
                .attribute("auth", "basic")
                .retrieve()
                .bodyToMono(Integer.class);

        StepVerifier.create(response)
                .expectNextMatches(r -> r == 15)
                .verifyComplete();
    }

    @Test
    public void headerRequestBearer() {
        var response = webClient
                .get()
                .uri("calculator/{firs}/{second}", 5, 10)
                .headers(h -> h.set("operation", "+"))
                .attribute("auth", "oAuth")
                .retrieve()
                .bodyToMono(Integer.class);

        StepVerifier.create(response)
                .expectNextMatches(r -> r == 15)
                .verifyComplete();
    }

    @Test
    public void headerRequestNoAttribute() {
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
