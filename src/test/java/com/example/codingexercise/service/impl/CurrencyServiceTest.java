package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.gateway.FrankfurterServiceGateway;
import com.example.codingexercise.gateway.dto.FrankfurterApiRateResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService currencyService;

    @Mock
    FrankfurterServiceGateway frankfurterServiceGateway;

    @Test
    void getCurrencyBaseUsdShouldReturnOneWhenInputUsd() {
        BigDecimal result = currencyService.getCurrencyBaseUsd(CurrencyCode.USD);

        assertThat(result, is(BigDecimal.ONE));
    }

    @Test
    void getCurrencyBaseUsdShouldReturnRateWhenInputNotUsd() {
        when(frankfurterServiceGateway.getCurrencyBaseUsd(CurrencyCode.TRY)).thenReturn(new FrankfurterApiRateResponse(Map.of("TRY", BigDecimal.TEN)));

        BigDecimal result = currencyService.getCurrencyBaseUsd(CurrencyCode.TRY);

        assertThat(result, is(BigDecimal.TEN));
    }

    @Test
    void getCurrencyBaseUsdShouldThrowNullPointerExceptionWhenInputNull() {
        assertThrows(NullPointerException.class, () -> currencyService.getCurrencyBaseUsd(null));
    }

}