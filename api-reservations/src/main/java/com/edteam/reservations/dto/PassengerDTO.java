package com.edteam.reservations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerDTO {

    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    @NotBlank(message = "lastName is mandatory")
    private String lastName;

    private String documentNumber;

    private String documentType;

    @Past(message = "birthday need to be a date in the past")
    private LocalDate birthday;

}
