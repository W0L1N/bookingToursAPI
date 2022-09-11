package com.kacwol.bookingToursAPI.destination.service;

import com.kacwol.bookingToursAPI.destination.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepo extends JpaRepository<Destination, Long> {
}
