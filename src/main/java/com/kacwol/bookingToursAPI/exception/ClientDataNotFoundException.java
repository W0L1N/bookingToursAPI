package com.kacwol.bookingToursAPI.exception;

public class ClientDataNotFoundException extends RuntimeException{

    public ClientDataNotFoundException(String message) {
        super(message);
    }
}
