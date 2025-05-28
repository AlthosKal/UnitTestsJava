package com.edteam.reservations.model;

import lombok.Data;

import java.util.List;

@Data
public class Reservation {

    private Long id;
    private List<Passenger> passengers;

    private Itinerary itinerary;

}
