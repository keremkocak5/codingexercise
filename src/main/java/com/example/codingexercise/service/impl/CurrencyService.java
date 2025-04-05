package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCodeEnum;
import com.example.codingexercise.gateway.FrankfurterServiceGateway;
import com.example.codingexercise.service.ICurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyService implements ICurrencyService {

    private final FrankfurterServiceGateway frankfurterServiceGateway;

    @Override
    public BigDecimal getCurrencyBaseUsd(CurrencyCodeEnum currencyCode) {
        return frankfurterServiceGateway
                .getCurrencyBaseUsd(currencyCode)
                .rates()
                .values()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("kerem"));
    }
}
