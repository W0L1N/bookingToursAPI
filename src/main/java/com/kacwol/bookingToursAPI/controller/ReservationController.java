package com.kacwol.bookingToursAPI.controller;


import com.kacwol.bookingToursAPI.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService service;

    @Autowired
    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping
    public void makeReservation(Authentication authentication, @RequestParam Long tourId, @RequestParam double price) {
        service.addReservation(authentication,tourId,price);
    }

    @PostMapping("/newPrice")
    private void offerNewPrice(Authentication authentication, @RequestParam Long offerId, @RequestParam double price) {
        service.offerNewPrice(authentication, offerId, price);
    }

    @PostMapping("/agree/{id}")
    public void agreeToOffer(Authentication authentication, @PathVariable Long id) {
        service.agree(authentication, id);
    }

}
