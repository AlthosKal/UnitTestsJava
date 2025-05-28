package com.edteam.reservations.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Passenger {

    private Long id;
    private String firstName;
    private String lastName;

    private String documentNumber;

    private String documentType;

    private LocalDate birthday;
}
