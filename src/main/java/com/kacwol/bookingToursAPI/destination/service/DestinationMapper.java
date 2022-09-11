package com.kacwol.bookingToursAPI.destination.service;

import com.kacwol.bookingToursAPI.destination.model.Destination;
import com.kacwol.bookingToursAPI.destination.model.DestinationDto;
import org.springframework.stereotype.Component;

@Component
public class DestinationMapper {

    public Destination dtoToEntity(DestinationDto dto) {
            return new Destination(
                    null,
                    dto.getName(),
                    dto.getDescription()
            );
    }

    public DestinationDto entityToDto(Destination entity) {
        return new DestinationDto(
                entity.getName(),
                entity.getDescription()
        );
    }
}
