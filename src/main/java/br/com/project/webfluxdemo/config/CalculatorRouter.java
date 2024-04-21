package br.com.project.webfluxdemo.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@AllArgsConstructor
public class CalculatorRouter {
    private final CalculatorHandler calculatorHandler;

    @Bean
    public RouterFunction<ServerResponse> calculatorRootRouter() {
        return RouterFunctions.route().path("calculator", this::calculatorRouterFunction).build();
    }

    public RouterFunction<ServerResponse> calculatorRouterFunction() {

        return RouterFunctions.route()
                .GET("{fist}/{second}", operationPredicate("+"), calculatorHandler::additionHandler)
                .GET("{fist}/{second}", operationPredicate("-"), calculatorHandler::subtractionHandler)
                .GET("{fist}/{second}", operationPredicate("*"), calculatorHandler::multiplicationHandler)
                .GET("{fist}/{second}", operationPredicate("/"), calculatorHandler::divisionHandler)
                .GET("{fist}/{second}", request -> ServerResponse.badRequest().bodyValue("Invalid operation"))
                .build();
    }

    private RequestPredicate operationPredicate(String operation) {
        return RequestPredicates.headers(headers -> operation
                .equals(headers.asHttpHeaders().toSingleValueMap().get("operation")));
    }

}
