package com.kacwol.bookingToursAPI.reservation.model;

import com.kacwol.bookingToursAPI.client.model.ClientData;
import com.kacwol.bookingToursAPI.exception.OfferAlreadyAcceptedException;
import com.kacwol.bookingToursAPI.exception.WrongPriceException;
import com.kacwol.bookingToursAPI.tour.model.Tour;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class ReservationOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Tour tour;

    @ManyToOne
    private ClientData clientData;

    private double price;

    private boolean agreedClient;

    private boolean agreedAdmin;

    public ReservationOffer(Tour tour, ClientData clientData, double price) {
        this.tour = tour;
        this.clientData = clientData;
        this.price = price;
        this.agreedAdmin = false;
        this.agreedClient = true;
    }

    public boolean bothAgreed() {
        return agreedClient && agreedAdmin;
    }

    public void clientOffer(double price) {

        if(bothAgreed()) throw new OfferAlreadyAcceptedException("Both sides already agreed.");

        if(price > tour.getInitialPrice()) throw new WrongPriceException("Offered price cannot be higher than initial price.");

        this.price = price;

        this.agreedClient = true;

        this.agreedAdmin = false;
    }

    public void adminOffer(double price) {

        if(bothAgreed()) throw new OfferAlreadyAcceptedException("Both sides already agreed.");

        if(price > tour.getInitialPrice()) throw new WrongPriceException("Offered price cannot be higher than initial price.");

        this.price = price;

        this.agreedAdmin = true;

        this.agreedClient = false;
    }

    public void agreeAdmin() {
        if(bothAgreed()) throw new OfferAlreadyAcceptedException("Both sides already agreed.");
        this.agreedAdmin = true;
    }

    public void agreeClient() {
        if(bothAgreed()) throw new OfferAlreadyAcceptedException("Both sides already agreed.");
        this.agreedClient = true;
    }


}
