package com.kacwol.bookingToursAPI.exception;

public class WrongPriceException extends RuntimeException{

    public WrongPriceException(String message) {
        super(message);
    }
}
