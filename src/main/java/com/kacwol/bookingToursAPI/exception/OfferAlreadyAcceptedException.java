package com.kacwol.bookingToursAPI.exception;

public class OfferAlreadyAcceptedException extends RuntimeException {
    public OfferAlreadyAcceptedException(String message) {
        super(message);
    }
}
