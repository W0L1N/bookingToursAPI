package com.kacwol.bookingToursAPI.exception;

public class UnauthorizedFunctionException extends RuntimeException {
    public UnauthorizedFunctionException(String message) {
        super(message);
    }
}
