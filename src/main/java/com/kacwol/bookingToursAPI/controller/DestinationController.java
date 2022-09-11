package com.kacwol.bookingToursAPI.controller;

import com.kacwol.bookingToursAPI.destination.model.Destination;
import com.kacwol.bookingToursAPI.destination.model.DestinationDto;
import com.kacwol.bookingToursAPI.destination.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/destination")
public class DestinationController {

    private final DestinationService service;

    @Autowired
    public DestinationController(DestinationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Destination> getAllDestinations() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public DestinationDto getDestinationById(@PathVariable Long id) {
        return service.getDtoById(id);
    }

    @PostMapping
    public void addDestination(@RequestBody DestinationDto destinationDto) {
        service.addDestination(destinationDto);
    }

    @PatchMapping("/name")
    public void changeName(Long id, String name) {
        service.changeName(id, name);
    }

    @PatchMapping("/description")
    public void changeDescription(Long id, String description) {
        service.changeDescription(id, description);
    }
}
