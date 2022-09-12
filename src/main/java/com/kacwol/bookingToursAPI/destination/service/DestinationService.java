package com.kacwol.bookingToursAPI.destination.service;

import com.kacwol.bookingToursAPI.destination.model.Destination;
import com.kacwol.bookingToursAPI.destination.model.DestinationDto;
import com.kacwol.bookingToursAPI.exception.DestinationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepo repo;

    private final DestinationMapper mapper;

    @Autowired
    public DestinationService(DestinationRepo repo, DestinationMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public void addDestination(DestinationDto destination) {
        repo.save(mapper.dtoToEntity(destination));
    }

    public Destination getEntityById(Long id) {
        return repo.findById(id).orElseThrow(() -> {
            throw new DestinationNotFoundException("Destination not found.");
        });
    }

    public DestinationDto getDtoById(Long id) {
        return mapper.entityToDto(
                getEntityById(id)
        );
    }

    public void changeName(Long id, String name) {
        Destination temp = getEntityById(id);
        repo.save(new Destination(
                        temp.getId(),
                        name,
                        temp.getDescription()
                )
        );
    }

    public void changeDescription(Long id, String description) {
        Destination temp = getEntityById(id);
        repo.save(new Destination(
                        temp.getId(),
                        temp.getName(),
                        description
                )
        );
    }

    public List<Destination> getAll() {
        return repo.findAll();
    }
}
