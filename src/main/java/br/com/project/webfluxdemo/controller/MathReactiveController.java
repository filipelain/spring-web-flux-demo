package br.com.project.webfluxdemo.controller;

import br.com.project.webfluxdemo.dto.MultiplyRequest;
import br.com.project.webfluxdemo.dto.Response;
import br.com.project.webfluxdemo.service.MathReactiveService;
import br.com.project.webfluxdemo.service.MathService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/math/reactive")
public class MathReactiveController {

    private final MathReactiveService mathService;


    @GetMapping("/square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {

        return mathService.findSquare(input);
    }

    @GetMapping("/table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }
    @GetMapping(value = "/table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }

    @PostMapping("/multiply")
    public Mono<Response>  multiply(@RequestBody Mono<MultiplyRequest> multiplyRequestMono){
        return  mathService.multiply(multiplyRequestMono);
    }
}
