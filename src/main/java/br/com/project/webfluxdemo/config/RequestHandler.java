package br.com.project.webfluxdemo.config;

import br.com.project.webfluxdemo.dto.InputFailedValidationResponse;
import br.com.project.webfluxdemo.dto.MultiplyRequest;
import br.com.project.webfluxdemo.dto.Response;
import br.com.project.webfluxdemo.execeptions.InputValidationException;
import br.com.project.webfluxdemo.service.MathReactiveService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class RequestHandler {

    private final MathReactiveService mathReactiveService;


    public Mono<ServerResponse> findSquareHandler(ServerRequest request) {
        var input = Integer.parseInt(request.pathVariable("input"));
        var responseMono = mathReactiveService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);

    }

    public Mono<ServerResponse> tableHandler(ServerRequest request) {
        var input = Integer.parseInt(request.pathVariable("input"));
        var responseFlux = mathReactiveService.multiplicationTable(input);
        return ServerResponse.ok().body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest request) {
        var input = Integer.parseInt(request.pathVariable("input"));
        var responseFlux = mathReactiveService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest request) {
        var body = request.bodyToMono(MultiplyRequest.class);
        var response = mathReactiveService.multiply(body);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(response, Response.class);
    }

    public Mono<ServerResponse> findSquareWithValidationHandler(ServerRequest request) {
        var input = Integer.parseInt(request.pathVariable("input"));

        if (input < 10 || input > 20) {
            return Mono.error(new InputValidationException(input));
        }

        var responseMono = mathReactiveService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);

    }


}
