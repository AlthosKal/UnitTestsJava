package com.edteam.reservations.dto;

import com.edteam.reservations.validator.CityFormatConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SegmentDTO {

    @CityFormatConstraint
    private String origin;

    @CityFormatConstraint
    private String destination;

    @NotBlank(message = "departure is mandatory")
    private String departure;

    @NotBlank(message = "arrival is mandatory")
    private String arrival;

    @NotBlank(message = "carrier is mandatory")
    private String carrier;
}
