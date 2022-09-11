package com.kacwol.bookingToursAPI.controller;

import com.kacwol.bookingToursAPI.tour.model.Tour;
import com.kacwol.bookingToursAPI.tour.model.TourResponseDto;
import com.kacwol.bookingToursAPI.tour.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tour")
public class TourController {

    private final TourService service;

    @Autowired
    public TourController(TourService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public TourResponseDto getTourById(@PathVariable Long id) {
        return service.getDtoById(id);
    }

    @GetMapping
    public List<Tour> getAllTours() {
        return service.getAll();
    }

}
