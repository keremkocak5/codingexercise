package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCodeEnum;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.service.ICurrencyService;
import com.example.codingexercise.service.IPackageConvertibleRateService;
import com.example.codingexercise.service.PackageRateServiceDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageRateServiceCurrencyConverterDecorator extends PackageRateServiceDecorator {

    public PackageRateServiceCurrencyConverterDecorator(IPackageConvertibleRateService wrappee) {
        super(wrappee);
    }

    @Autowired
    private ICurrencyService currencyService;

    @Override
    public PackageResponse createPackage(PackageRequest packageRequest, CurrencyCodeEnum currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        PackageResponse packageResponse = super.createPackage(packageRequest, currencyCode);
        return recalculateTotalPrice(rate, packageResponse);
    }

    @Override
    public PackageResponse getProductPackage(String id, CurrencyCodeEnum currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        PackageResponse packageResponse = super.getProductPackage(id, currencyCode);
        return recalculateTotalPrice(rate, packageResponse);
    }

    @Override
    public List<PackageResponse> getProductPackage(CurrencyCodeEnum currencyCode) {
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
                packageResponse.productIds(),
                packageResponse.totalPrice().multiply(rate),
                packageResponse.currencyCode());
    }

}
