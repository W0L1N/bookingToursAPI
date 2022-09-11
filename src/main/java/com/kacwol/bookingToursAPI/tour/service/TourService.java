package com.kacwol.bookingToursAPI.tour.service;

import com.kacwol.bookingToursAPI.destination.model.Destination;
import com.kacwol.bookingToursAPI.destination.service.DestinationService;
import com.kacwol.bookingToursAPI.tour.model.Tour;
import com.kacwol.bookingToursAPI.tour.model.TourDto;
import com.kacwol.bookingToursAPI.tour.model.TourResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TourService {

    private final TourRepo repo;

    private final TourMapper mapper;

    private final DestinationService destinationService;

    @Autowired
    public TourService(TourRepo repo, TourMapper mapper, DestinationService destinationService) {
        this.repo = repo;
        this.mapper = mapper;
        this.destinationService = destinationService;
    }

    public void addTour(TourDto tour) {
        repo.save(mapper.dtoToEntity(tour));
    }

    public Tour getEntityById(Long id) {
        return repo.findById(id).orElseThrow(RuntimeException::new);
    }

    public TourResponseDto getDtoById(Long id) {
        return mapper.entityToDto(
                getEntityById(id)
        );
    }

    public List<Tour> getAll() {
        return repo.findAll();
    }

    public void changeDestination(Long id,Long destinationId) {
        Tour tempTour = getEntityById(id);
        Destination tempDestination = destinationService.getEntityById(destinationId);

        repo.save(new Tour(
                tempTour.getId(),
                tempDestination,
                tempTour.getGuide(),
                tempTour.getStartDate(),
                tempTour.getEndDate(),
                tempTour.getInitialPrice()
            )
        );
    }

    public void changeStartDate(Long id, LocalDateTime startDate) {
        Tour tempTour = getEntityById(id);

        if(startDate.isAfter(tempTour.getEndDate())) throw new RuntimeException();

        repo.save(new Tour(
                tempTour.getId(),
                tempTour.getDestination(),
                tempTour.getGuide(),
                startDate,
                tempTour.getEndDate(),
                tempTour.getInitialPrice()
                )
        );
    }

    public void changeEndDate(Long id, LocalDateTime endTime) {
        Tour tempTour = getEntityById(id);

        if(endTime.isBefore(tempTour.getStartDate())) throw new RuntimeException();

        repo.save(new Tour(
                tempTour.getId(),
                tempTour.getDestination(),
                tempTour.getGuide(),
                tempTour.getStartDate(),
                endTime,
                tempTour.getInitialPrice()
                )
        );
    }

    public void changeSuggestedPrice(Long id, double suggestedPrice) {
        if(suggestedPrice < 0) throw new RuntimeException();

        Tour tempTour = getEntityById(id);

        repo.save(new Tour(
                tempTour.getId(),
                tempTour.getDestination(),
                tempTour.getGuide(),
                tempTour.getStartDate(),
                tempTour.getEndDate(),
                suggestedPrice
                )
        );
    }


}
