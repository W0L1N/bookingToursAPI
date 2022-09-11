package com.kacwol.bookingToursAPI.tour.service;

import com.kacwol.bookingToursAPI.destination.model.Destination;
import com.kacwol.bookingToursAPI.destination.model.DestinationDto;
import com.kacwol.bookingToursAPI.destination.service.DestinationService;
import com.kacwol.bookingToursAPI.tour.model.Tour;
import com.kacwol.bookingToursAPI.tour.model.TourDto;
import com.kacwol.bookingToursAPI.tour.model.TourResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TourMapper {

    private DestinationService destinationService;

    @Autowired
    public TourMapper(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    public TourResponseDto entityToDto(Tour entity) {
        DestinationDto destination = destinationService.getDtoById(
                entity.getDestination().getId()
        );

        return new TourResponseDto(
                destination,
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getInitialPrice()
        );
    }


    public Tour dtoToEntity(TourDto dto) {

        if(dto.getStartDate().isAfter(dto.getEndDate())) throw new RuntimeException();

        if(dto.getSuggestedPrice() < 0) throw new RuntimeException();

        Destination destination = destinationService.getEntityById(dto.getDestinationId());

        return Tour.builder()
                .destination(destination)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .initialPrice(dto.getSuggestedPrice())
                .build();
    }
}
