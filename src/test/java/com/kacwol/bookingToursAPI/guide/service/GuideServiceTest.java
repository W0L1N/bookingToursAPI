package com.kacwol.bookingToursAPI.guide.service;

import com.kacwol.bookingToursAPI.exception.UserAlreadyExistsException;
import com.kacwol.bookingToursAPI.guide.model.GuideData;
import com.kacwol.bookingToursAPI.guide.model.GuideRegistrationData;
import com.kacwol.bookingToursAPI.security.userdata.UserData;
import com.kacwol.bookingToursAPI.security.userdata.UserDataRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GuideServiceTest {

    @Mock
    private UserDataRepo userDataRepo;

    @Mock
    private GuideDataRepo guideDataRepo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private GuideService guideService;

    @Test
    public void addGuide_shouldWork() {

        Long userId = null;
        Long guideId = null;
        String username = "username";
        String password = "password";
        List<String> roles = List.of("GUIDE");

        String name = "name";
        String surname = "surname";

        UserData userData = new UserData(userId, username, password, roles);

        GuideRegistrationData guideRegistrationData = new GuideRegistrationData(username, password,name, surname);

        Mockito.when(encoder.encode(password)).thenReturn(password);
        Mockito.when(userDataRepo.save(new UserData(userId, username, password, roles))).thenReturn(userData);

        ArgumentCaptor<GuideData> captor = ArgumentCaptor.forClass(GuideData.class);
        guideService.addGuide(guideRegistrationData);
        Mockito.verify(guideDataRepo).save(captor.capture());

        GuideData expected = new GuideData(guideId, name, surname, userData);

        GuideData actual = captor.getValue();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addGuide_shouldThrowUserAlreadyExistsException() {
        String username = "username";
        String password = "password";

        String name = "name";
        String surname = "surname";

        GuideRegistrationData guideRegistrationData = new GuideRegistrationData(username, password,name, surname);

        Mockito.when(userDataRepo.existsByUsername(username)).thenReturn(true);

        Assert.assertThrows(UserAlreadyExistsException.class, () -> guideService.addGuide(guideRegistrationData));
    }

}
