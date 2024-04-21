package br.com.project.webfluxdemo.controller;

import br.com.project.webfluxdemo.dto.Response;
import br.com.project.webfluxdemo.service.MathService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/math")
public class MathController {

    private final MathService mathService;


    @GetMapping("/square/{input}")
    public Response findSquare(@PathVariable int input) {
        return mathService.findSquare(input);
    }

    @GetMapping("/table/{input}")
    public List<Response> multiplicationTable(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }
}
