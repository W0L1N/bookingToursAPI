package com.kacwol.bookingToursAPI.client.service;

import com.kacwol.bookingToursAPI.client.model.ClientDataDto;
import com.kacwol.bookingToursAPI.exception.UnauthorizedFunctionException;
import com.kacwol.bookingToursAPI.security.userdata.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@WithMockUser
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientServiceTest {

    @Mock
    private ClientRepo repo;

    @Mock
    private ClientMapper mapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void addClientData_shouldThrowUnauthorizedFunctionException() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = "name";
        String surname = "surname";
        String passportNumber = "passportNumber";

        ClientDataDto clientData = new ClientDataDto(name, surname, passportNumber);

        Mockito.when(repo.existsByPassportNumber(passportNumber)).thenReturn(true);

        Assert.assertThrows(UnauthorizedFunctionException.class, () -> clientService.addClientData(auth, clientData));

    }

}
