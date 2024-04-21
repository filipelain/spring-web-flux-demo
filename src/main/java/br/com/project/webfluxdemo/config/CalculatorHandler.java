package br.com.project.webfluxdemo.config;

import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CalculatorHandler {

    public Mono<ServerResponse> additionHandler(ServerRequest request) {
        return process(
                request,
                (first, second) -> ServerResponse
                        .ok()
                        .bodyValue(first + second)
        );

    }

    public Mono<ServerResponse> subtractionHandler(ServerRequest request) {
        return process(
                request,
                (first, second) -> ServerResponse
                        .ok()
                        .bodyValue(first - second)
        );
    }

    public Mono<ServerResponse> multiplicationHandler(ServerRequest request) {
        return process(
                request,
                (first, second) -> ServerResponse
                        .ok()
                        .bodyValue(first * second)
        );
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest request) {
        return process(
                request,
                (first, second) -> second != 0 ?
                        ServerResponse.ok().bodyValue(first / second) :
                        ServerResponse.badRequest().bodyValue("the divisor could be not a zero value")
        );

    }

    public Mono<ServerResponse> process(
            ServerRequest request,
            BiFunction<Integer, Integer, Mono<ServerResponse>> opLogic
    ) {
        int first = getValueOf(request, "fist");
        int second = getValueOf(request, "second");
        return opLogic.apply(first, second);
    }

    private int getValueOf(ServerRequest request, String key) {
        return Integer.parseInt(request.pathVariable(key));
    }
}
