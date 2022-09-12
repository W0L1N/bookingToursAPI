package com.kacwol.bookingToursAPI.reservation.service;

import com.kacwol.bookingToursAPI.client.model.ClientData;
import com.kacwol.bookingToursAPI.client.service.ClientService;
import com.kacwol.bookingToursAPI.destination.model.Destination;
import com.kacwol.bookingToursAPI.exception.OfferAlreadyAcceptedException;
import com.kacwol.bookingToursAPI.guide.model.GuideData;
import com.kacwol.bookingToursAPI.reservation.model.Reservation;
import com.kacwol.bookingToursAPI.reservation.model.ReservationOffer;
import com.kacwol.bookingToursAPI.security.userdata.UserData;
import com.kacwol.bookingToursAPI.security.userdata.UserService;
import com.kacwol.bookingToursAPI.tour.model.Tour;
import com.kacwol.bookingToursAPI.tour.service.TourService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ReservationServiceTestForAdmin {

    @Mock
    private ReservationRepo repo;

    @Mock
    private ReservationOfferService offerService;

    @Mock
    private UserService userService;

    @Mock
    private ClientService clientService;

    @Mock
    private TourService tourService;

    @InjectMocks
    private ReservationService reservationService;


    @Test
    @WithMockUser
    public void offerNewPrice_shouldWork_OnAdmin() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Long offerId = 1L;
        double initialPrice = 1200;

        Tour tour = new Tour(
                1L,
                new Destination(),
                new GuideData(),
                LocalDateTime.MIN,
                LocalDateTime.MAX,
                initialPrice
        );

        ClientData clientData = new ClientData();
        double price = 1000;
        boolean agreedAdmin = false;
        boolean agreedUser = true;

        double newPrice = 1100;

        ReservationOffer offer = new ReservationOffer(offerId, tour, clientData, price, agreedUser,agreedAdmin);

        Mockito.when(offerService.getEntityById(offerId)).thenReturn(offer);
        Mockito.when(userService.containsAuthority(auth,"ADMIN")).thenReturn(true);

        boolean newAgreedAdmin = true;
        boolean newAgreedUser = false;

        ArgumentCaptor<ReservationOffer> captor = ArgumentCaptor.forClass(ReservationOffer.class);
        reservationService.offerNewPrice(auth, offerId, newPrice);
        Mockito.verify(offerService).addOffer(captor.capture());

        ReservationOffer expected = new ReservationOffer(offerId, tour, clientData, newPrice, newAgreedUser, newAgreedAdmin);

        ReservationOffer actual = captor.getValue();

        Assert.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void offerNewPrice_shouldThrowOfferAlreadyAcceptedException_OnAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Long offerId = 1L;
        double initialPrice = 1200;

        Tour tour = new Tour(
                1L,
                new Destination(),
                new GuideData(),
                LocalDateTime.MIN,
                LocalDateTime.MAX,
                initialPrice
        );

        Long userId = 1L;
        String username = "username";
        String password = "password";
        List<String> roles = List.of("USER");
        UserData userData = new UserData(userId, username, password, roles);


        Long clientId = 1L;
        String name = "name";
        String surname = "surname";
        String passportNumber = "passportNumber";

        ClientData clientData = new ClientData(clientId,name,surname,passportNumber,userData);
        double price = 1100;
        boolean agreedAdmin = true;
        boolean agreedUser = true;

        ReservationOffer offer = new ReservationOffer(offerId, tour, clientData, price, agreedUser,agreedAdmin);

        double newPrice = 1000;

        Mockito.when(offerService.getEntityById(offerId)).thenReturn(offer);
        Mockito.when(userService.containsAuthority(auth,"ADMIN")).thenReturn(true);
        Mockito.when(userService.getEntityByUsername(auth.getName())).thenReturn(userData);
        Mockito.when(clientService.getEntityByUserId(userId)).thenReturn(clientData);

        Assert.assertThrows(OfferAlreadyAcceptedException.class, () -> reservationService.offerNewPrice(auth, offerId, newPrice));

    }

    @Test
    @WithMockUser
    public void agree_shouldWork_OnAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Long offerId = 1L;

        Tour tour = new Tour();
        ClientData clientData = new ClientData();
        double price = 1100;
        boolean agreedAdmin = false;
        boolean agreedUser = true;

        ReservationOffer offer = new ReservationOffer(offerId, tour, clientData, price, agreedUser,agreedAdmin);

        Mockito.when(offerService.getEntityById(offerId)).thenReturn(offer);
        Mockito.when(userService.containsAuthority(auth,"ADMIN")).thenReturn(true);

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        reservationService.agree(auth, offerId);
        Mockito.verify(repo).save(captor.capture());

        Reservation expected = new Reservation(null, tour, clientData, price);
        Reservation actual = captor.getValue();

        Assert.assertEquals(expected, actual);

    }

    @Test
    @WithMockUser
    public void agree_shouldThrowOfferAlreadyAcceptedException_OnAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Long offerId = 1L;

        Tour tour = new Tour();
        ClientData clientData = new ClientData();
        double price = 1100;
        boolean agreedAdmin = true;
        boolean agreedUser = true;

        ReservationOffer offer = new ReservationOffer(offerId, tour, clientData, price, agreedUser,agreedAdmin);

        Mockito.when(offerService.getEntityById(offerId)).thenReturn(offer);
        Mockito.when(userService.containsAuthority(auth,"ADMIN")).thenReturn(true);

        Assert.assertThrows(OfferAlreadyAcceptedException.class, () -> reservationService.agree(auth, offerId));

    }
}
