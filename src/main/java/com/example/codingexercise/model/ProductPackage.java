package com.example.codingexercise.model;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductPackage {
    private final String id;
    private final String name;
    private final String description;
    private final List<String> productIds;

    public ProductPackage(String id, String name, String description, List<String> productIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productIds = productIds;
    }

}
