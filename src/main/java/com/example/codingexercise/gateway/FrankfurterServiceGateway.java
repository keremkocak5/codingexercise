package com.example.codingexercise.gateway;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.enums.ErrorCode;
import com.example.codingexercise.exception.CodingExerciseRuntimeException;
import com.example.codingexercise.gateway.dto.FrankfurterApiRateResponse;
import com.example.codingexercise.util.Constant;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class FrankfurterServiceGateway {

    private final RestTemplate frankfurterRestTemplate;

    @Value("${rest.frankfurter.url}")
    private String frankfurterServiceUrl;

    public @NonNull FrankfurterApiRateResponse getCurrencyBaseUsd(@NonNull CurrencyCode currencyCode) {
        try {
            return frankfurterRestTemplate.getForObject(frankfurterServiceUrl + "/v1/latest?base=" + Constant.USD + "&symbols={currencyCode}", FrankfurterApiRateResponse.class, currencyCode.name());
        } catch (Exception e) {
            log.error("FrankfurterServiceGateway.getCurrencyBaseUsd failed for currencyCode {}.", currencyCode);
            log.error("FrankfurterServiceGateway.getCurrencyBaseUsd failed.", e);
            throw new CodingExerciseRuntimeException(ErrorCode.RATE_CONNECTION_ERROR);
        }
    }
}
