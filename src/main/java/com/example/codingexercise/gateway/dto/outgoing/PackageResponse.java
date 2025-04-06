package com.example.codingexercise.gateway.dto.outgoing;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record PackageResponse(UUID id,
                              String name,
                              String description,
                              List<ProductResponse> products,
                              BigDecimal totalPrice,
                              String currencyCode) {
}
