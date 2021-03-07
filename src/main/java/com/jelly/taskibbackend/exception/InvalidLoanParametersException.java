package com.jelly.taskibbackend.exception;

public class InvalidLoanParametersException extends RuntimeException {

    public InvalidLoanParametersException() {
        super("Invalid loan parameters");
    }
}
