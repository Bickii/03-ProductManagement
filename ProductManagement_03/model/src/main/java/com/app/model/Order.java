package com.app.model;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Order {
    private Customer customer;
    private Product product;
    private Integer quantity;
    protected LocalDate estimatedRealizationDate;
}
