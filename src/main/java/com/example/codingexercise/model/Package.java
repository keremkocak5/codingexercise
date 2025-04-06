package com.example.codingexercise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Package {

    private final UUID id;
    private final String name;
    private final String description;
    private final LinkedList<Product> products;
    private final BigDecimal totalPrice;
    private final String currencyCode;

}
