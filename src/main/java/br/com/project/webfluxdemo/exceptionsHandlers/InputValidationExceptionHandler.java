package br.com.project.webfluxdemo.exceptionsHandlers;

import br.com.project.webfluxdemo.dto.InputFailedValidationResponse;
import br.com.project.webfluxdemo.execeptions.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationExceptionHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> inputValidationException(InputValidationException ex) {
        var response = new InputFailedValidationResponse(ex.getErrorCode(), ex.getInput(), ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
