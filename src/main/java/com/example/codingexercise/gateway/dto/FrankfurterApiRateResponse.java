package com.example.codingexercise.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

public record FrankfurterApiRateResponse(@JsonProperty("rates") Map<String, BigDecimal> rates) {
}
