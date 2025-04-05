package com.example.codingexercise.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Product {
    private final String id;
    private final String name;
    private final BigDecimal price;
    private final String currencyCode;

    public Product(String id, String name, BigDecimal price, String currencyCode) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currencyCode = currencyCode;
    }

}
