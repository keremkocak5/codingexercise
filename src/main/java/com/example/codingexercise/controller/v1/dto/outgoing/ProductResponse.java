package com.example.codingexercise.controller.v1.dto.outgoing;

import java.math.BigDecimal;

public record ProductResponse(String id,
                              String name,
                              BigDecimal price,
                              String currencyCode) {
}
