package br.com.project.webfluxdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl("http://localhost:8080")
                .filter(this::sessionToken)
                .build();
    }

//    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
//        System.out.println("Generate Session token");
//        var client = ClientRequest.from(request)
//                .headers(h -> h.setBearerAuth("some-token"))
//                .build();
//
//        return ex.exchange(client);
//    }
    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
        var client = request.attribute("auth")
                .map(r -> r.equals("basic") ? withBasicAuth(request) : withOAuth(request))
                .orElse(request);

        return ex.exchange(client);
    }

    private ClientRequest withBasicAuth(ClientRequest clientRequest) {
        return ClientRequest.from(clientRequest)
                .headers(h -> h.setBasicAuth("user", "password"))
                .build();
    }

    private ClientRequest withOAuth(ClientRequest clientRequest) {
        return ClientRequest.from(clientRequest)
                .headers(h -> h.setBearerAuth("some-token"))
                .build();
    }

}
