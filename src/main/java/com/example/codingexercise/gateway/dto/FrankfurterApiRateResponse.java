package com.example.codingexercise.gateway.dto;

import java.math.BigDecimal;
import java.util.Map;

public record FrankfurterApiRateResponse(Map<String, BigDecimal> rates) {
}
