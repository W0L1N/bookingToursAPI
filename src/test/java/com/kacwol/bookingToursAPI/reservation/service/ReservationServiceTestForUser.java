package com.kacwol.bookingToursAPI.reservation.service;

import com.kacwol.bookingToursAPI.client.model.ClientData;
import com.kacwol.bookingToursAPI.client.service.ClientService;
import com.kacwol.bookingToursAPI.destination.model.Destination;
import com.kacwol.bookingToursAPI.exception.OfferAlreadyAcceptedException;
import com.kacwol.bookingToursAPI.exception.UnauthorizedFunctionException;
import com.kacwol.bookingToursAPI.exception.WrongPriceException;
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
public class ReservationServiceTestForUser {

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
    @WithMockUser(username = "user", authorities = "USER")
    public void addReservation_shouldWork() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Long userId = 1L;
        String username = "username";
        String password = "password";
        String role = "USER";
        UserData userData = new UserData(userId, username, password, List.of(role));

        Long clientId = 1L;
        String clientName = "clientName";
        String clientSurname = "clientSurname";
        String passportNumber = "passportNumber";

        ClientData clientData = new ClientData(clientId, clientName, clientSurname, passportNumber, userData);

        Long destinationId = 1L;
        String destinationName = "destinationName";
        String destinationDescription = "destinationDescription";

        Destination destination = new Destination(destinationId, destinationName, destinationDescription);

        Long tourId = 1L;
        LocalDateTime startDate = LocalDateTime.of(2002, 9, 25, 12, 35);
        LocalDateTime endDate = LocalDateTime.of(2020, 9, 25, 12, 35);
        double initialPrice = 1000;

        double newPrice = 800;

        Long guideId = 1L;
        String guideName = "guideName";
        String guideSurname = "guideSurname";

        GuideData guideData = new GuideData(guideId, guideName, guideSurname, new UserData());

        Tour tour = new Tour(tourId, destination, guideData, startDate, endDate, initialPrice);


        Mockito.when(userService.getEntityByUsername(auth.getName())).thenReturn(userData);
        Mockito.when(clientService.getEntityByUserId(userId)).thenReturn(clientData);
        Mockito.when(tourService.getEntityById(tourId)).thenReturn(tour);

        ArgumentCaptor<ReservationOffer> captor = ArgumentCaptor.forClass(ReservationOffer.class);
        reservationService.addReservation(auth, tourId, newPrice);
        Mockito.verify(offerService).addOffer(captor.capture());

        ReservationOffer expected = new ReservationOffer(tour, clientData, newPrice);

        ReservationOffer actual = captor.getValue();

        Assert.assertEquals(expected, actual);

    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void addReservation_shouldThrowWrongPriceException() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Long userId = 1L;
        String username = "username";
        String password = "password";
        String role = "USER";
        UserData userData = new UserData(userId, username, password, List.of(role));

        Long clientId = 1L;
        String clientName = "clientName";
        String clientSurname = "clientSurname";
        String passportNumber = "passportNumber";

        ClientData clientData = new ClientData(clientId, clientName, clientSurname, passportNumber, userData);

        Long destinationId = 1L;
        String destinationName = "destinationName";
        String destinationDescription = "destinationDescription";

        Destination destination = new Destination(destinationId, destinationName, destinationDescription);

        Long tourId = 1L;
        LocalDateTime startDate = LocalDateTime.of(2002, 9, 25, 12, 35);
        LocalDateTime endDate = LocalDateTime.of(2020, 9, 25, 12, 35);
        double initialPrice = 1000;
        double newPrice = 1200;


        Long guideId = 1L;
        String guideName = "guideName";
        String guideSurname = "guideSurname";

        GuideData guideData = new GuideData(guideId, guideName, guideSurname, new UserData());

        Tour tour = new Tour(tourId, destination, guideData, startDate, endDate, initialPrice);


        Mockito.when(userService.getEntityByUsername(auth.getName())).thenReturn(userData);
        Mockito.when(clientService.getEntityByUserId(userId)).thenReturn(clientData);
        Mockito.when(tourService.getEntityById(tourId)).thenReturn(tour);

        Assert.assertThrows(WrongPriceException.class, () -> reservationService.addReservation(auth, tourId, newPrice));
    }


    @Test
    @WithMockUser
    public void offerNewPrice_shouldWork_OnUser() {
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

        ClientData clientData = new ClientData(clientId, name, surname, passportNumber, userData);
        double price = 1100;
        boolean agreedAdmin = true;
        boolean agreedUser = false;


        double newPrice = 1000;
        boolean newAgreedAdmin = false;
        boolean newAgreedUser = true;

        ReservationOffer offer = new ReservationOffer(offerId, tour, clientData, price, agreedUser, agreedAdmin);

        Mockito.when(offerService.getEntityById(offerId)).thenReturn(offer);
        Mockito.when(userService.containsAuthority(auth, "ADMIN")).thenReturn(false);
        Mockito.when(userService.getEntityByUsername(auth.getName())).thenReturn(userData);
        Mockito.when(clientService.getEntityByUserId(userId)).thenReturn(clientData);


        ArgumentCaptor<ReservationOffer> captor = ArgumentCaptor.forClass(ReservationOffer.class);
        reservationService.offerNewPrice(auth, offerId, newPrice);
        Mockito.verify(offerService).addOffer(captor.capture());

        ReservationOffer expected = new ReservationOffer(offerId, tour, clientData, newPrice, newAgreedUser, newAgreedAdmin);

        ReservationOffer actual = captor.getValue();

        Assert.assertEquals(expected, actual);

    }

    @Test
    @WithMockUser
    public void offerNewPrice_shouldThrowUnauthorizedFunctionException_OnUser() {
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

        ClientData clientData = new ClientData(clientId, name, surname, passportNumber, userData);
        double price = 1100;
        boolean agreedAdmin = true;
        boolean agreedUser = false;

        Long anotherClientId = 2L;
        ClientData anotherClientData = new ClientData(anotherClientId, name, surname, passportNumber, userData);

        ReservationOffer offer = new ReservationOffer(offerId, tour, clientData, price, agreedUser, agreedAdmin);

        double newPrice = 1000;

        Mockito.when(offerService.getEntityById(offerId)).thenReturn(offer);
        Mockito.when(userService.containsAuthority(auth, "ADMIN")).thenReturn(false);
        Mockito.when(userService.getEntityByUsername(auth.getName())).thenReturn(userData);
        Mockito.when(clientService.getEntityByUserId(userId)).thenReturn(anotherClientData);

        Assert.assertThrows(UnauthorizedFunctionException.class, () -> reservationService.offerNewPrice(auth, offerId, newPrice));

    }

    @Test
    @WithMockUser
    public void offerNewPrice_shouldThrowOfferAlreadyAcceptedException_OnUser() {
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

        ClientData clientData = new ClientData(clientId, name, surname, passportNumber, userData);
        double price = 1100;
        boolean agreedAdmin = true;
        boolean agreedUser = true;

        ReservationOffer offer = new ReservationOffer(offerId, tour, clientData, price, agreedUser, agreedAdmin);

        double newPrice = 1000;

        Mockito.when(offerService.getEntityById(offerId)).thenReturn(offer);
        Mockito.when(userService.containsAuthority(auth, "ADMIN")).thenReturn(false);
        Mockito.when(userService.getEntityByUsername(auth.getName())).thenReturn(userData);
        Mockito.when(clientService.getEntityByUserId(userId)).thenReturn(clientData);

        Assert.assertThrows(OfferAlreadyAcceptedException.class, () -> reservationService.offerNewPrice(auth, offerId, newPrice));


    }

    @Test
    @WithMockUser
    public void agree_shouldWork_OnUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Long offerId = 1L;

        Tour tour = new Tour();
        double price = 1100;
        boolean agreedAdmin = true;
        boolean agreedUser = false;

        Long userId = 1L;
        String username = "username";
        String password = "password";
        List<String> roles = List.of("USER");
        UserData userData = new UserData(userId, username, password, roles);


        Long clientId = 1L;
        String name = "name";
        String surname = "surname";
        String passportNumber = "passportNumber";

        ClientData clientData = new ClientData(clientId, name, surname, passportNumber, userData);


        ReservationOffer offer = new ReservationOffer(offerId, tour, clientData, price, agreedUser, agreedAdmin);

        Mockito.when(offerService.getEntityById(offerId)).thenReturn(offer);
        Mockito.when(userService.containsAuthority(auth, "ADMIN")).thenReturn(false);
        Mockito.when(userService.getEntityByUsername(auth.getName())).thenReturn(userData);
        Mockito.when(clientService.getEntityByUserId(userId)).thenReturn(clientData);

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        reservationService.agree(auth, offerId);
        Mockito.verify(repo).save(captor.capture());

        Reservation expected = new Reservation(null, tour, clientData, price);
        Reservation actual = captor.getValue();

        Assert.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser
    public void agree_shouldThrowUnauthorizedFunctionException() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Long offerId = 1L;

        Tour tour = new Tour();
        double price = 1100;
        boolean agreedAdmin = true;
        boolean agreedUser = false;

        Long userId = 1L;
        String username = "username";
        String password = "password";
        List<String> roles = List.of("USER");
        UserData userData = new UserData(userId, username, password, roles);


        Long clientId = 1L;
        String name = "name";
        String surname = "surname";
        String passportNumber = "passportNumber";

        ClientData clientData = new ClientData(clientId, name, surname, passportNumber, userData);

        Long anotherClientId = 2L;
        ClientData anotherClientData =  new ClientData(anotherClientId, name, surname, passportNumber, userData);

        ReservationOffer offer = new ReservationOffer(offerId, tour, clientData, price, agreedUser, agreedAdmin);

        Mockito.when(offerService.getEntityById(offerId)).thenReturn(offer);
        Mockito.when(userService.containsAuthority(auth, "ADMIN")).thenReturn(false);
        Mockito.when(userService.getEntityByUsername(auth.getName())).thenReturn(userData);
        Mockito.when(clientService.getEntityByUserId(userId)).thenReturn(anotherClientData);


        Assert.assertThrows(UnauthorizedFunctionException.class, () -> reservationService.agree(auth, offerId));

    }

    @Test
    @WithMockUser
    public void agree_shouldThrowOfferAlreadyAccepted () {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Long offerId = 1L;

        Tour tour = new Tour();
        double price = 1100;
        boolean agreedAdmin = true;
        boolean agreedUser = true;

        Long userId = 1L;
        String username = "username";
        String password = "password";
        List<String> roles = List.of("USER");
        UserData userData = new UserData(userId, username, password, roles);


        Long clientId = 1L;
        String name = "name";
        String surname = "surname";
        String passportNumber = "passportNumber";

        ClientData clientData = new ClientData(clientId, name, surname, passportNumber, userData);

        ReservationOffer offer = new ReservationOffer(offerId, tour, clientData, price, agreedUser, agreedAdmin);

        Mockito.when(offerService.getEntityById(offerId)).thenReturn(offer);
        Mockito.when(userService.containsAuthority(auth, "ADMIN")).thenReturn(false);
        Mockito.when(userService.getEntityByUsername(auth.getName())).thenReturn(userData);
        Mockito.when(clientService.getEntityByUserId(userId)).thenReturn(clientData);

        Assert.assertThrows(OfferAlreadyAcceptedException.class, () -> reservationService.agree(auth, offerId));

    }


}
