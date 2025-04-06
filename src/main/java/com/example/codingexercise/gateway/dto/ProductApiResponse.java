package com.example.codingexercise.gateway.dto;

import java.math.BigDecimal;

public record ProductApiResponse(String id, String name, BigDecimal usdPrice) {
}
