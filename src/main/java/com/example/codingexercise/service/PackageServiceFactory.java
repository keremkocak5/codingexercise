package com.example.codingexercise.service;

import com.example.codingexercise.service.impl.PackageServiceCurrencyConverterDecorator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PackageServiceFactory {

    private final IPackageService packageService;
    private final PackageServiceCurrencyConverterDecorator packageServiceCurrencyConverterDecorator;

    public IPackageService getPackageService(boolean isCurrency) {
        return isCurrency ? packageService : packageServiceCurrencyConverterDecorator;
    }

}
