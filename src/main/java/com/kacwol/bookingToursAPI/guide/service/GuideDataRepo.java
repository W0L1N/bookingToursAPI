package com.kacwol.bookingToursAPI.guide.service;

import com.kacwol.bookingToursAPI.guide.model.GuideData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuideDataRepo extends JpaRepository<GuideData, Long> {
}
