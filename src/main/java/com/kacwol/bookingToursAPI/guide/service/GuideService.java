package com.kacwol.bookingToursAPI.guide.service;

import com.kacwol.bookingToursAPI.exception.UserAlreadyExistsException;
import com.kacwol.bookingToursAPI.guide.model.GuideData;
import com.kacwol.bookingToursAPI.guide.model.GuideRegistrationData;
import com.kacwol.bookingToursAPI.security.userdata.UserData;
import com.kacwol.bookingToursAPI.security.userdata.UserDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideService {

    private final UserDataRepo userDataRepo;

    private final GuideDataRepo guideDataRepo;

    private final PasswordEncoder encoder;

    @Autowired
    public GuideService(UserDataRepo userDataRepo, GuideDataRepo guideDataRepo, PasswordEncoder encoder) {
        this.userDataRepo = userDataRepo;
        this.guideDataRepo = guideDataRepo;
        this.encoder = encoder;
    }

    public List<GuideData> getAll(){
        return guideDataRepo.findAll();
    }

    public void addGuide(GuideRegistrationData data) {
        if(userDataRepo.existsByUsername(data.getUsername())) throw new UserAlreadyExistsException("User with given username already exists.");

        UserData userData = userDataRepo.save(new UserData(
                null,
                data.getUsername(),
                encoder.encode(data.getPassword()),
                List.of("USER","GUIDE")
                )
        );

        GuideData guideData =  new GuideData(
                null,
                data.getName(),
                data.getSurname(),
                userData
        );
        guideDataRepo.save(guideData);
    }


}
