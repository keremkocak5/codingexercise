package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCodeEnum;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.service.ICurrencyService;
import com.example.codingexercise.service.IPackageService;
import com.example.codingexercise.service.PackageServiceDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageServiceCurrencyConverterDecorator extends PackageServiceDecorator {

    public PackageServiceCurrencyConverterDecorator(IPackageService wrappee) {
        super(wrappee);
    }

    @Autowired
    private ICurrencyService currencyService;

    @Override
    public PackageResponse createPackage(PackageRequest packageRequest, CurrencyCodeEnum currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        PackageResponse packageResponse = super.createPackage(packageRequest, currencyCode);
        return getConvertedPackageResponse(rate, packageResponse);
    }

    @Override
    public PackageResponse getProductPackage(String id, CurrencyCodeEnum currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        PackageResponse packageResponse = super.getProductPackage(id, currencyCode);
        return getConvertedPackageResponse(rate, packageResponse);
    }

    @Override
    public List<PackageResponse> getProductPackage(CurrencyCodeEnum currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        List<PackageResponse> packageResponses = super.getProductPackage(currencyCode);
        return packageResponses
                .stream()
                .map(packageResponse -> getConvertedPackageResponse(rate, packageResponse))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean deletePackage(String id) {
        return super.deletePackage(id);
    }

    private static PackageResponse getConvertedPackageResponse(BigDecimal rate, PackageResponse packageResponse) {
        return new PackageResponse(packageResponse.id(),
                packageResponse.name(),
                packageResponse.description(),
                packageResponse.productIds(),
                packageResponse.totalCost().multiply(rate),
                packageResponse.currencyCode());
    }

}
