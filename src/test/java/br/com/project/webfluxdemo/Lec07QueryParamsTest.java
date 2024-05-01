package br.com.project.webfluxdemo;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec07QueryParamsTest extends BaseTest {

    @Autowired
    private WebClient webClient;


    @Test
    void queryParamTestString() {
        var url = "http://localhost:8080/jobs/search?count={count}&page={page}";
        var uri = UriComponentsBuilder.fromUriString(url)
                .build(10, 20);

        var result = webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void queryParamTestBuilder() {
        var result = webClient
                .get()
                .uri(b -> b.path("jobs/search").query("count={count}&page={page}").build(15, 25))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void queryParamTestMap() {
        var mappedQuery = Map.of(
                "count", 11,
                "page", 21
        );

        var result = webClient
                .get()
                .uri(b -> b.path("jobs/search")
                        .query("count={count}&page={page}")
                        .build(mappedQuery)
                )
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }
}
