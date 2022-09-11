package com.kacwol.bookingToursAPI.client.service;

import com.kacwol.bookingToursAPI.client.model.ClientData;
import com.kacwol.bookingToursAPI.client.model.ClientDataDto;
import com.kacwol.bookingToursAPI.security.userdata.UserData;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientData dtoToEntity(ClientDataDto dto, UserData userData) {
        return new ClientData(
                null,
                dto.getName(),
                dto.getSurname(),
                dto.getPassportNumber(),
                userData
        );
    }

    public ClientDataDto entityToDto(ClientData entity) {
        return new ClientDataDto(
                entity.getName(),
                entity.getSurname(),
                entity.getPassportNumber()
        );
    }
}
