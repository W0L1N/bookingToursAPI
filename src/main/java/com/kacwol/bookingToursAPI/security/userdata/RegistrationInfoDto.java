package com.kacwol.bookingToursAPI.security.userdata;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegistrationInfoDto {

    private String username;

    private String password;
}
