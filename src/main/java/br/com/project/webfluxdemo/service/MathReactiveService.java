package br.com.project.webfluxdemo.service;

import static br.com.project.webfluxdemo.service.SleepUtil.sleepSeconds;

import br.com.project.webfluxdemo.dto.MultiplyRequest;
import br.com.project.webfluxdemo.dto.Response;
import java.math.BigDecimal;
import java.time.Duration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MathReactiveService {

    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> new BigDecimal(input * input))
                .map(Response::new);
    }

    public Flux<Response>multiplicationTable(int input){
        return Flux.range(1,10)
//                .doOnNext(i-> sleepSeconds(1)) this is an block thread
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i-> System.out.println("reactive service : "+i))
                .map(i-> new Response(new BigDecimal(i * input)));


    }

    public Mono<Response> multiply(Mono<MultiplyRequest> multiplyRequest){
        return multiplyRequest
                .map(dto-> new BigDecimal(dto.getFist()*dto.getSecond()))
                .map(Response::new);
    }
}
