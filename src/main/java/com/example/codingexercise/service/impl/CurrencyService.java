package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCodeEnum;
import com.example.codingexercise.gateway.FrankfurterServiceGateway;
import com.example.codingexercise.service.ICurrencyService;
import com.example.codingexercise.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyService implements ICurrencyService {

    private final FrankfurterServiceGateway frankfurterServiceGateway;

    @Override
    public BigDecimal getCurrencyBaseUsd(CurrencyCodeEnum currencyCode) {
        if (Constant.USD.equals(currencyCode.name())) {
            throw new RuntimeException("kerem");
        }
        return frankfurterServiceGateway
                .getCurrencyBaseUsd(currencyCode)
                .rates()
                .values()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("kerem"));
    }
}
