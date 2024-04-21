package br.com.project.webfluxdemo.execeptions;

import lombok.Getter;

@Getter
public class InputValidationException extends RuntimeException {
    private static final String MESSAGE = "allowed range is 10 -20";
    private static final int ERROR_CODE = 100;
    private final int input;


    public InputValidationException(int input) {
        super(MESSAGE);
        this.input = input;
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
