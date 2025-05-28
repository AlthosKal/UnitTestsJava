package com.edteam.reservations.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Price {

    private Long id;
    private BigDecimal totalPrice;

    private BigDecimal totalTax;

    private BigDecimal basePrice;

}
