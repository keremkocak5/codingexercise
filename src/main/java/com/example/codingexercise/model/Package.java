package com.example.codingexercise.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class Package {
    private final String id;
    private final String name;
    private final String description;
    private final List<Product> products;
    private final BigDecimal totalPrice;
    private final String currencyCode;

    public Package(String id, String name, String description, List<Product> products, BigDecimal totalPrice, String currencyCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
        this.totalPrice = totalPrice;
        this.currencyCode = currencyCode;
    }

}
