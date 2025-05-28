package com.edteam.reservations.model;

import lombok.Data;

@Data
public class Segment {

    private Long id;
    private String origin;

    private String destination;

    private String departure;

    private String arrival;

    private String carrier;
}
