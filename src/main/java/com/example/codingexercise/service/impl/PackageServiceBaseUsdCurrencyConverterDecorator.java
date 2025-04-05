package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.service.ICurrencyService;
import com.example.codingexercise.service.IPackageConvertibleRateService;
import com.example.codingexercise.service.PackageServiceDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageServiceBaseUsdCurrencyConverterDecorator extends PackageServiceDecorator {

    public PackageServiceBaseUsdCurrencyConverterDecorator(IPackageConvertibleRateService wrappee) {
        super(wrappee);
    }

    @Autowired
    private ICurrencyService currencyService;

    @Override
    public PackageResponse createPackage(PackageRequest packageRequest, CurrencyCode currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        PackageResponse packageResponse = super.createPackage(packageRequest, currencyCode);
        return recalculateTotalPrice(rate, packageResponse);
    }

    @Override
    public PackageResponse getProductPackage(String id, CurrencyCode currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        PackageResponse packageResponse = super.getProductPackage(id, currencyCode);
        return recalculateTotalPrice(rate, packageResponse);
    }

    @Override
    public List<PackageResponse> getProductPackage(CurrencyCode currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        List<PackageResponse> packageResponses = super.getProductPackage(currencyCode);
        return packageResponses
                .stream()
                .map(packageResponse -> recalculateTotalPrice(rate, packageResponse))
                .collect(Collectors.toUnmodifiableList());
    }

    private static PackageResponse recalculateTotalPrice(BigDecimal rate, PackageResponse packageResponse) {
        return new PackageResponse(packageResponse.id(),
                packageResponse.name(),
                packageResponse.description(),
                packageResponse.products(),
                packageResponse.totalPrice().multiply(rate),
                packageResponse.currencyCode());
    }

}
