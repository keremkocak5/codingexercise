package com.example.codingexercise.gateway.dto.outgoing;

import com.example.codingexercise.model.Product;

import java.math.BigDecimal;
import java.util.List;

public record PackageResponse(String id,
                              String name,
                              String description,
                              List<Product> products,
                              BigDecimal totalPrice,
                              String currencyCode) {
}
