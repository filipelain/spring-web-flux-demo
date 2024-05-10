package br.com.project.webfluxdemo.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec09AssignmentTest extends BaseTest {

    private static final String format = "%d %s %d = %d";
    @Autowired
    private WebClient webClient;

    @Test
    public void assignmentRequest() {
        var fist = 5;

        var result = Flux.range(1, 5)
                .flatMap(i -> Flux.just("+", "-", "*", "/")
                        .flatMap(op -> send(fist, i, op))
                )
                .doOnNext(System.out::println);

        StepVerifier.create(result)
                .expectNextCount(20)
                .verifyComplete();
    }

    private Mono<String> send(int fist, int second, String op) {
        return this.webClient
                .get()
                .uri("calculator/{firs}/{second}", fist, second)
                .headers(h -> h.set("operation", op))
                .retrieve()
                .bodyToMono(Integer.class)
                .map(r -> String.format(format, fist, op, second, r));
    }
}
