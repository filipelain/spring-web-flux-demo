package br.com.project.webfluxdemo.webtestclient;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.project.webfluxdemo.config.RequestHandler;
import br.com.project.webfluxdemo.config.RouterConfig;
import br.com.project.webfluxdemo.dto.Response;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
public class Lec05RouterFunctionTest {

//    @Autowired
//    private RouterConfig routerConfig;

    private WebTestClient webTestClient;

    @Autowired
    private ApplicationContext ctx;

    @MockBean
    private RequestHandler requestHandler;

//    @BeforeAll
//    public void setup() {
//        this.webTestClient = WebTestClient
//                .bindToRouterFunction(routerConfig.routerRootRouter())
//                .build();
//    }

    @BeforeAll
    public void setup() {
        this.webTestClient = WebTestClient
                .bindToApplicationContext(ctx)
                .build();
    }


    @Test
    public void testRouterFunction() {

        var response = new Response(new BigDecimal(1));

        when(requestHandler.findSquareHandler(any()))
                .thenReturn(ServerResponse.ok().bodyValue(response));

        webTestClient
                .get()
                .uri("/router/square/{input}", 11)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Response.class)
                .value(result -> assertThat(result.getOutput())
                        .isEqualTo(new BigDecimal(1)));


    }

}
