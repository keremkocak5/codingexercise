package com.example.codingexercise.gateway.dto;

import java.math.BigDecimal;
import java.util.List;

public record Rate(List<BigDecimal> rates) {
}
