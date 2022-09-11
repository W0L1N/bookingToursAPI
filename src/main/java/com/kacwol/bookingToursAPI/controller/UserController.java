package com.kacwol.bookingToursAPI.controller;

import com.kacwol.bookingToursAPI.guide.model.GuideData;
import com.kacwol.bookingToursAPI.guide.model.GuideRegistrationData;
import com.kacwol.bookingToursAPI.guide.service.GuideService;
import com.kacwol.bookingToursAPI.security.userdata.RegistrationInfoDto;
import com.kacwol.bookingToursAPI.security.userdata.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final GuideService guideService;

    @Autowired
    public UserController(UserService userService, GuideService guideService) {
        this.userService = userService;
        this.guideService = guideService;
    }

    @PostMapping
    public void register(@RequestBody RegistrationInfoDto info) {
        userService.register(info);
    }

    @GetMapping("/guide")
    public List<GuideData> getAllGuides() {
        return guideService.getAll();
    }

    @PostMapping("/guide")
    public void addGuide(@RequestBody GuideRegistrationData data) {
        guideService.addGuide(data);
    }
}
