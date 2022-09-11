package com.kacwol.bookingToursAPI.controller;

import com.kacwol.bookingToursAPI.tour.model.TourDto;
import com.kacwol.bookingToursAPI.tour.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/tour/edit")
public class TourEditController {

    private final TourService service;

    @Autowired
    public TourEditController(TourService service) {
        this.service = service;
    }

    @PostMapping
    public void addTour(@RequestBody TourDto tour) {
        service.addTour(tour);
    }

    @PatchMapping("/destination/{id}/{destinationId}")
    public void changeDestination(Long id, Long destinationId) {
        service.changeDestination(id, destinationId);
    }

    @PatchMapping("/startdate")
    public void changeStartDate(Long id, LocalDateTime  startDate) {
        service.changeStartDate(id, startDate);
    }

    @PatchMapping("/enddate")
    public void changeEndDate(Long id, LocalDateTime endDate) {
        service.changeEndDate(id, endDate);
    }

    @PatchMapping("/suggestedprice")
    public void changeSuggestedPrice(Long id, double suggestedPrice) {
        service.changeSuggestedPrice(id, suggestedPrice);
    }
}
