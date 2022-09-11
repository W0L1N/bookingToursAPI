package com.kacwol.bookingToursAPI.guide.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GuideRegistrationData {

    private String username;

    private String password;

    private String name;

    private String surname;
}
