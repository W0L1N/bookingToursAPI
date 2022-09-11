package com.kacwol.bookingToursAPI.reservation.service;

import com.kacwol.bookingToursAPI.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
}
