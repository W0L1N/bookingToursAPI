package com.kacwol.bookingToursAPI.tour.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kacwol.bookingToursAPI.destination.model.DestinationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class TourResponseDto {

    private DestinationDto destination;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime endDate;

    private double suggestedPrice;

}
