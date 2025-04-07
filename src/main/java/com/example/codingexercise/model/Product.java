package com.example.codingexercise.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Product {

    private final String id;
    private final UUID packageId;
    private final String name;
    private final BigDecimal price;
    private final String currencyCode;

}
