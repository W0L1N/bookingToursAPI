package com.kacwol.bookingToursAPI.controller;

import com.kacwol.bookingToursAPI.client.model.ClientDataDto;
import com.kacwol.bookingToursAPI.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }


    @GetMapping
    public List<ClientDataDto> getAllClients() {
        return service.getAllClients();
    }

    @PostMapping("/add")
    public void addClient(Authentication authentication, @RequestBody ClientDataDto client){
        service.addClientData(authentication, client);
    }
}
