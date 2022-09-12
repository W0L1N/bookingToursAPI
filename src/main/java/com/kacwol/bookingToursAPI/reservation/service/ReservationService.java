package com.kacwol.bookingToursAPI.reservation.service;

import com.kacwol.bookingToursAPI.client.model.ClientData;
import com.kacwol.bookingToursAPI.client.service.ClientService;
import com.kacwol.bookingToursAPI.exception.UnauthorizedFunctionException;
import com.kacwol.bookingToursAPI.exception.WrongPriceException;
import com.kacwol.bookingToursAPI.reservation.model.Reservation;
import com.kacwol.bookingToursAPI.reservation.model.ReservationOffer;
import com.kacwol.bookingToursAPI.security.userdata.UserData;
import com.kacwol.bookingToursAPI.security.userdata.UserService;
import com.kacwol.bookingToursAPI.tour.model.Tour;
import com.kacwol.bookingToursAPI.tour.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepo repo;

    private final ReservationOfferService offerService;

    private final UserService userService;

    private final ClientService clientService;

    private final TourService tourService;

    @Autowired
    public ReservationService(ReservationRepo repo, ReservationOfferService offerService, UserService userService, ClientService clientService, TourService tourService) {
        this.repo = repo;
        this.offerService = offerService;
        this.userService = userService;
        this.clientService = clientService;
        this.tourService = tourService;
    }


    public void addReservation(Authentication auth, Long tourId, double price) {

        if (userService.containsAuthority(auth, "ADMIN")) {
            throw new UnauthorizedFunctionException("You're not client. Go to work instead of playing with API!!!!");
        }

        UserData userData = userService.getEntityByUsername(auth.getName());
        ClientData clientData = clientService.getEntityByUserId(userData.getId());

        Tour tour = tourService.getEntityById(tourId);

        if (price > tour.getInitialPrice())
            throw new WrongPriceException("Given price cannot be higher than initial price.");

        offerService.addOffer(new ReservationOffer(
                tour,
                clientData,
                price
        ));
    }

    public void offerNewPrice(Authentication auth, Long offerId, double price) {

        ReservationOffer offer = offerService.getEntityById(offerId);

        if (userService.containsAuthority(auth, "ADMIN")) {
            offer.adminOffer(price);
        } else {
            checkClientHasAccessToOffer(auth, offer);

            offer.clientOffer(price);
        }
        offerService.addOffer(offer);
    }

    public void agree(Authentication auth, Long offerId) {

        ReservationOffer offer = offerService.getEntityById(offerId);

        if(userService.containsAuthority(auth, "ADMIN")){
            offer.agreeAdmin();
        } else {
            checkClientHasAccessToOffer(auth, offer);
            offer.agreeClient();
        }

        if(offer.bothAgreed()) repo.save(new Reservation(
                null,
                offer.getTour(),
                offer.getClientData(),
                offer.getPrice()
                )
        );
    }

    private void checkClientHasAccessToOffer (Authentication auth, ReservationOffer offer) {
        UserData userData = userService.getEntityByUsername(auth.getName());
        ClientData clientData = clientService.getEntityByUserId(userData.getId());
        if(!clientData.equals(offer.getClientData())){
            throw new UnauthorizedFunctionException("You don't have access to this offer.");
        }
    }

}
