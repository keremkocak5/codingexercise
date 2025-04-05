package com.example.codingexercise.gateway.dto.outgoing;

import java.math.BigDecimal;

public record ProductResponse(String id,
                              String name,
                              BigDecimal price,
                              String currencyCode) {
}
