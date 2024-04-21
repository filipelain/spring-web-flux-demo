package br.com.project.webfluxdemo.controller;

import br.com.project.webfluxdemo.dto.Response;
import br.com.project.webfluxdemo.execeptions.InputValidationException;
import br.com.project.webfluxdemo.service.MathReactiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/math/reactive/validation")
public class ReactiveMathValidationController {
    private final MathReactiveService mathService;


    @GetMapping("/square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20) {
            throw new InputValidationException(input);
        }
        return mathService.findSquare(input);
    }


    @GetMapping("/square/{input}/mono")
    public Mono<Response> findSquareMonoPipeline(@PathVariable int input) {
      return Mono.just(input)
              .handle((integer,sink)->{
                    if(integer >= 10 && integer <=20) sink.next(integer);
                    else sink.error(new InputValidationException(integer));
              })
              .cast(Integer.class)
              .flatMap(mathService::findSquare);
    }

    @GetMapping("/square/{input}/assignment")
    public Mono<ResponseEntity<Response>> findSquareAssignment(@PathVariable int input) {
      return Mono.just(input)
              .filter(integer -> integer >= 10 && integer <= 20)
              .flatMap(mathService::findSquare)
              .map(ResponseEntity::ok)
              .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
