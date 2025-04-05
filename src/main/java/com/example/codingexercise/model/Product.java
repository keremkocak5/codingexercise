package com.example.codingexercise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Product {

    private final String id;
    private final String name;
    private final BigDecimal price;
    private final String currencyCode;

}
