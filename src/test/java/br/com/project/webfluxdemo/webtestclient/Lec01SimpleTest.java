package br.com.project.webfluxdemo.webtestclient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import br.com.project.webfluxdemo.dto.Response;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class Lec01SimpleTest {

    @Autowired
    private WebTestClient webClient;


    @Test
    public void stepVerifierTest() {
        var response = webClient
                .get()
                .uri("/math/reactive/square/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Response.class)
                .getResponseBody();

        StepVerifier.create(response)
                .expectNextMatches(r -> r.getOutput().equals(new BigDecimal(25)))
                .verifyComplete();
    }

    @Test
    public void FluentAssertionTest() {
        webClient
                .get()
                .uri("/math/reactive/square/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(result -> assertThat(result.getOutput()).isEqualTo(new BigDecimal(25)));
    }
}
