package br.com.project.webfluxdemo.config;

import br.com.project.webfluxdemo.dto.InputFailedValidationResponse;
import br.com.project.webfluxdemo.execeptions.InputValidationException;
import java.util.function.BiFunction;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
@AllArgsConstructor
public class RouterConfig {
    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> routerRootRouter() {
        return RouterFunctions.route()
                .path("router",this::serverResponseRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("square/{input}", RequestPredicates.path("*/1?"),requestHandler::findSquareHandler)
                .GET("square/{input}", request -> ServerResponse.badRequest().bodyValue("only 10 at 19 allowed"))
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/stream/{input}", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiplyHandler)
                .GET("validation/square/{input}", requestHandler::findSquareWithValidationHandler)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (err, req) -> {
            var ex = (InputValidationException) err;
            var response = new InputFailedValidationResponse(ex);
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
