package com.kacwol.bookingToursAPI.tour.service;

import com.kacwol.bookingToursAPI.tour.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepo extends JpaRepository<Tour,Long> {
}
