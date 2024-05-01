package br.com.project.webfluxdemo;

import br.com.project.webfluxdemo.dto.Response;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec01GetSingleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;


    @Test
    public void blockTest(){
       var response = webClient
                .get()
                .uri("math/reactive/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

         System.out.println(response);
    }

    @Test
    public void stepVerifierTest(){
       var response = webClient
                .get()
                .uri("math/reactive/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.getOutput().equals(new BigDecimal(25)))
                .verifyComplete();
    }

}
