package com.example.codingexercise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class Package {

    private final String id; // kerem burayi UUID yapayim mi
    private final String name;
    private final String description;
    private final List<Product> products;
    private final BigDecimal totalPrice;
    private final String currencyCode;

}
