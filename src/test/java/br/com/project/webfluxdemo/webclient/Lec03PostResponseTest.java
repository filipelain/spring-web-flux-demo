package br.com.project.webfluxdemo.webclient;

import br.com.project.webfluxdemo.dto.MultiplyRequest;
import br.com.project.webfluxdemo.dto.Response;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec03PostResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void postTest() {
        var response = webClient
                .post()
                .uri("math/reactive/multiply")
                .bodyValue(getMultiplyRequest())
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.getOutput().equals(new BigDecimal(50)))
                .verifyComplete();
    }


    private MultiplyRequest getMultiplyRequest() {
        return new MultiplyRequest(5, 10);
    }


}
