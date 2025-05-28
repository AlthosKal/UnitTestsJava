package com.edteam.reservations.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceDTO {

    private BigDecimal totalPrice;

    private BigDecimal totalTax;

    private BigDecimal basePrice;
}
