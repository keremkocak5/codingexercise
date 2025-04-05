package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCodeEnum;
import com.example.codingexercise.service.IPackageConvertibleRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageServiceFactory {

    private final IPackageConvertibleRateService packageService;
    private final PackageRateServiceCurrencyConverterDecorator packageServiceCurrencyConverterDecorator;

    public IPackageConvertibleRateService getPackageService(Optional<CurrencyCodeEnum> currency) {
        return currency.isEmpty() || CurrencyCodeEnum.USD.equals(currency.get()) ? packageService : packageServiceCurrencyConverterDecorator ;
    }

}
