package br.com.project.webfluxdemo.service;

import static br.com.project.webfluxdemo.service.SleepUtil.sleepSeconds;

import br.com.project.webfluxdemo.dto.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class MathService {

    public Response findSquare(int input) {
        return new Response(new BigDecimal(input * input));
    }

    public List<Response> multiplicationTable(int input) {
        return IntStream.rangeClosed(1, 10)
                .peek(i -> sleepSeconds(1))
                .mapToObj(i -> new Response(new BigDecimal(i * input)))
                .toList();
    }
}
