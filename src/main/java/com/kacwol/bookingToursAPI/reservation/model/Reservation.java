package com.kacwol.bookingToursAPI.reservation.model;

import com.kacwol.bookingToursAPI.client.model.ClientData;
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Tour tour;

    @ManyToOne
    private ClientData client;

    private double actualPrice;
}
