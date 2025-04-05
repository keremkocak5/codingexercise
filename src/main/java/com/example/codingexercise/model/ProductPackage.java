package com.example.codingexercise.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class ProductPackage {
    private final String id;
    private final String name;
    private final String description;
    private final List<String> productIds;
    private final BigDecimal totalPrice;
    private final String currencyCode;

    public ProductPackage(String id, String name, String description, List<String> productIds, BigDecimal totalPrice, String currencyCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productIds = productIds;
        this.totalPrice = totalPrice;
        this.currencyCode = currencyCode;
    }

}
