package com.kacwol.bookingToursAPI.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClientDataDto {
    private String name;

    private String surname;

    private String passportNumber;
}
