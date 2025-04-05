package com.example.codingexercise.service.impl;

import com.example.codingexercise.service.IPackageConvertibleRateService;
import com.example.codingexercise.service.impl.PackageRateServiceCurrencyConverterDecorator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PackageServiceFactory {

    private final IPackageConvertibleRateService packageService;
    private final PackageRateServiceCurrencyConverterDecorator packageServiceCurrencyConverterDecorator;

    public IPackageConvertibleRateService getPackageService(boolean isCurrency) {
        return isCurrency ? packageServiceCurrencyConverterDecorator : packageService;
    }

}
