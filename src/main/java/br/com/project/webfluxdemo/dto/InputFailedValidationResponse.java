package br.com.project.webfluxdemo.dto;

import br.com.project.webfluxdemo.execeptions.InputValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class InputFailedValidationResponse {
    private int errorCode;
    private int input;
    private String massage;

    public InputFailedValidationResponse(InputValidationException ex) {
        this.errorCode = ex.getErrorCode();
        this.input = ex.getInput();
        this.massage = ex.getMessage();

    }
}
