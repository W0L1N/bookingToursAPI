package com.kacwol.bookingToursAPI.reservation.service;

import com.kacwol.bookingToursAPI.exception.ReservationOfferNotFoundException;
import com.kacwol.bookingToursAPI.reservation.model.ReservationOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationOfferService {

    private final ReservationOfferRepo repo;

    @Autowired
    public ReservationOfferService(ReservationOfferRepo repo) {
        this.repo = repo;
    }

    public ReservationOffer getEntityById(Long id) {
        return repo.findById(id).orElseThrow(() -> {
           throw new ReservationOfferNotFoundException("Reservation offer not found. ");
        });
    }

    public void addOffer(ReservationOffer entity) {
        repo.save(entity);
    }
}
