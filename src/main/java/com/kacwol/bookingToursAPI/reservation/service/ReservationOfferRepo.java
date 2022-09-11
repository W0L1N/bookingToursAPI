package com.kacwol.bookingToursAPI.reservation.service;

import com.kacwol.bookingToursAPI.reservation.model.ReservationOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationOfferRepo extends JpaRepository<ReservationOffer, Long> {

}
