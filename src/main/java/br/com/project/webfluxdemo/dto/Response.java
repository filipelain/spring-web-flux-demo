package br.com.project.webfluxdemo.dto;


import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Response {
    private BigDecimal output;
    private Date date = new Date();

    public Response(BigDecimal output) {
        this.output = output;
    }

}
