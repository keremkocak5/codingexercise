package com.example.codingexercise.gateway;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.exception.CodingExerciseRuntimeException;
import com.example.codingexercise.gateway.dto.FrankfurterApiRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FrankfurterServiceGatewayTest {

    @InjectMocks
    FrankfurterServiceGateway frankfurterServiceGateway;

    @Mock
    private RestTemplate frankfurterRestTemplate;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(frankfurterServiceGateway, "frankfurterServiceUrl", "someurl");
    }

    @Test
    void getCurrencyBaseUsdShouldReturnFrankfurterApiRateResponse() {
        when(frankfurterRestTemplate.getForObject("someurl/v1/latest?base=USD&symbols={currencyCode}", FrankfurterApiRateResponse.class, CurrencyCode.TRY.name()))
                .thenReturn(new FrankfurterApiRateResponse(Map.of("TRY", BigDecimal.TEN)));

        FrankfurterApiRateResponse result = frankfurterServiceGateway.getCurrencyBaseUsd(CurrencyCode.TRY);

        assertThat(result.rates().size(), is(1));
        assertThat(result.rates().get("TRY"), is(BigDecimal.TEN));
    }

    @Test
    void getCurrencyBaseUsdShouldThrowExceptionWhenConnectionFails() {
        when(frankfurterRestTemplate.getForObject("someurl/v1/latest?base=USD&symbols={currencyCode}", FrankfurterApiRateResponse.class, CurrencyCode.TRY.name()))
                .thenThrow(new RuntimeException("bad!"));

        assertThrows(CodingExerciseRuntimeException.class, () -> frankfurterServiceGateway.getCurrencyBaseUsd(CurrencyCode.TRY));
    }

}