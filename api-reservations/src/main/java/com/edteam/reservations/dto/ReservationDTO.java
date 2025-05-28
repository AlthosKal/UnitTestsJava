package com.edteam.reservations.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ReservationDTO {

    private Long id;

    @Valid
    @NotEmpty(message = "You need at least one passenger")
    private List<PassengerDTO> passengers;

    @Valid
    private ItineraryDTO itinerary;

}
