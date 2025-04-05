package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.enums.ErrorCode;
import com.example.codingexercise.exception.CodingExerciseRuntimeException;
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
    public BigDecimal getCurrencyBaseUsd(CurrencyCode currencyCode) {
        if (Constant.USD.equals(currencyCode.name())) {
            throw new CodingExerciseRuntimeException(ErrorCode.INVALID_BASE_RATE);
        }
        return frankfurterServiceGateway
                .getCurrencyBaseUsd(currencyCode)
                .rates()
                .values()
                .stream()
                .findFirst()
                .orElseThrow(() -> new CodingExerciseRuntimeException(ErrorCode.RATE_CONNECTION_ERROR));
    }
}
