package com.edteam.reservations.model;

import lombok.Data;

import java.util.List;

@Data
public class Itinerary {
    private Long id;
    private List<Segment> segment;

    private Price price;

}
