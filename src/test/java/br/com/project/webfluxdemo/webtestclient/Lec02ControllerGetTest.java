package br.com.project.webfluxdemo.webtestclient;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.project.webfluxdemo.controller.MathReactiveController;
import br.com.project.webfluxdemo.dto.Response;
import br.com.project.webfluxdemo.service.MathReactiveService;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@WebFluxTest(MathReactiveController.class)
public class Lec02ControllerGetTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private MathReactiveService mathService;

    @Test
    public void FluentAssertionTest() {
        var expectedValue = new BigDecimal(25);
        var response = new Response(expectedValue);

        when(mathService.findSquare(any(int.class)))
                .thenReturn(Mono.just(response));

        webClient
                .get()
                .uri("/math/reactive/square/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(result -> assertThat(result.getOutput()).isEqualTo(expectedValue));
    }

    @Test
    public void FluentAssertionMultiResponseTest() {
        var response = Flux.range(1, 10)
                .map(i -> new Response(new BigDecimal(i * 2)));


        when(mathService.multiplicationTable(any(int.class)))
                .thenReturn(response);

        webClient
                .get()
                .uri("/math/reactive/table/{input}", 2)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Response.class)
                .hasSize(10)
                .value(result -> {
                    Flux.range(1, 10)
                            .map(i -> new BigDecimal(i * 2))
                            .zipWith(Flux.fromIterable(result), (expected, actual) -> Tuples.of(expected, actual.getOutput()))
                            .doOnNext(tuple -> assertThat(tuple.getT1()).isEqualTo(tuple.getT2()))
                            .blockLast();
                });
    }

    @Test
    public void FluentAssertionStreamResponseTest() {
        var response = Flux.range(1, 3)
                .map(i -> new Response(new BigDecimal(i * 2)))
                .delayElements(Duration.ofSeconds(1));


        when(mathService.multiplicationTable(any(int.class)))
                .thenReturn(response);

        webClient
                .get()
                .uri("/math/reactive/table/{input}/stream", 2)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .expectBodyList(Response.class)
                .hasSize(3);
    }
}
