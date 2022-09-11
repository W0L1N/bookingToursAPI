package com.kacwol.bookingToursAPI.client.service;

import com.kacwol.bookingToursAPI.client.model.ClientData;
import com.kacwol.bookingToursAPI.client.model.ClientDataDto;
import com.kacwol.bookingToursAPI.exception.ClientDataNotFoundException;
import com.kacwol.bookingToursAPI.exception.UnauthorizedFunctionException;
import com.kacwol.bookingToursAPI.security.userdata.UserData;
import com.kacwol.bookingToursAPI.security.userdata.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepo repo;

    private final ClientMapper mapper;

    private final UserService userService;


    @Autowired
    public ClientService(ClientRepo repo, ClientMapper mapper, UserService userService) {
        this.repo = repo;
        this.mapper = mapper;
        this.userService = userService;
    }

    public void addClientData(Authentication auth, ClientDataDto client) {
        if (repo.existsByPassportNumber(client.getPassportNumber())) throw new UnauthorizedFunctionException("Client data already exist.");
        UserData userData = userService.getEntityByUsername(auth.getName());
        repo.save(mapper.dtoToEntity(client, userData));
    }

    public List<ClientDataDto> getAllClients() {
        return repo.findAll()
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    public ClientData getEntityByUserId(Long id) {
        return repo.findByUserDataId(id).orElseThrow(()->{
            throw new ClientDataNotFoundException("Client data not found, you need to fill");
        });
    }
}
