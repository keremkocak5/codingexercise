package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.service.IPackageConvertibleRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageServiceFactory {

    private final IPackageConvertibleRateService packageService;
    private final PackageServiceBaseUsdCurrencyConverterDecorator packageServiceCurrencyConverterDecorator;

    public IPackageConvertibleRateService getPackageService(Optional<CurrencyCode> currency) {
        return currency.isEmpty() || CurrencyCode.USD.equals(currency.get()) ? packageService : packageServiceCurrencyConverterDecorator;
    }

}
