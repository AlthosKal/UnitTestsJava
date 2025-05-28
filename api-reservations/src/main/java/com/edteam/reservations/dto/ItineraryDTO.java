package com.edteam.reservations.dto;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class ItineraryDTO {
    @Valid
    private List<SegmentDTO> segment;

    private PriceDTO price;

}
