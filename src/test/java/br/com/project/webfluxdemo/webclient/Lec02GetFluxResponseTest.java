package br.com.project.webfluxdemo.webclient;

import br.com.project.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec02GetFluxResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void fluxTest(){
        var response = webClient
                .get()
                .uri("math/reactive/table/{input}", 5)
                .retrieve()
                .bodyToFlux(Response.class);

        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void fluxStreamTest(){
        var response = webClient
                .get()
                .uri("math/reactive/table/{input}/stream", 5)
                .retrieve()
                .bodyToFlux(Response.class);

        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();
    }

}
