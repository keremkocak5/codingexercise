package com.example.codingexercise.gateway;

import com.example.codingexercise.enums.CurrencyCodeEnum;
import com.example.codingexercise.gateway.dto.Rate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class FrankfurterServiceGateway {

    private final RestTemplate frankfurterRestTemplate;

    @Value("${rest.frankfurter.url}")
    private String frankfurterServiceUrl;

    public Rate getCurrencyBaseUsd(CurrencyCodeEnum currencyCode) {
        try {
            return frankfurterRestTemplate.getForObject(frankfurterServiceUrl + "/v1/latest?base=USD&symbols={currencyCode}", Rate.class, currencyCode.name());
        } catch (Exception e) {
            log.error("FrankfurterServiceGateway.getCurrencyBaseUsd failed for currencyCodeEnum {}.", currencyCode);
            log.error("FrankfurterServiceGateway.getCurrencyBaseUsd failed.", e);
            throw e;
        }
    }
}
