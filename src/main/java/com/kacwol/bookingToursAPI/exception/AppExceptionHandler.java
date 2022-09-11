package com.kacwol.bookingToursAPI.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {
            DestinationNotFoundException.class,
            UsernameNotFoundException.class,
            ClientDataNotFoundException.class,
            ReservationOfferNotFoundException.class,
    })
    public ResponseEntity<String> handleNotFoundException(RuntimeException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            WrongPriceException.class,
            UserAlreadyExistsException.class,
            OfferAlreadyAcceptedException.class
    })
    public ResponseEntity<String> handleBadRequestException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            UnauthorizedFunctionException.class
    })
    public ResponseEntity<String> handleUnAuthorizedException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
