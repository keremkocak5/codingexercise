package com.example.codingexercise.gateway.dto.outgoing;

import java.math.BigDecimal;
import java.util.List;

public record PackageResponse(String id,
                              String name,
                              String description,
                              List<String> productIds,
                              BigDecimal totalPrice,
                              String currencyCode) {
}
