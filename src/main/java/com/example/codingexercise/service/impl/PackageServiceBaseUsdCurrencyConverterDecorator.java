package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.enums.ErrorCode;
import com.example.codingexercise.exception.CodingExerciseRuntimeException;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.model.Product;
import com.example.codingexercise.service.ICurrencyService;
import com.example.codingexercise.service.IPackageConvertibleRateService;
import com.example.codingexercise.service.IPackageService;
import com.example.codingexercise.service.PackageServiceDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PackageServiceBaseUsdCurrencyConverterDecorator extends PackageServiceDecorator {

    public PackageServiceBaseUsdCurrencyConverterDecorator(IPackageConvertibleRateService wrappee) {
        super(wrappee);
    }

    @Autowired
    private ICurrencyService currencyService;
    @Autowired
    private IPackageService packageService;

    @Override
    public PackageResponse createPackage(PackageRequest packageRequest, CurrencyCode currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        PackageResponse packageResponse = super.createPackage(packageRequest, currencyCode);
        try {
            return recalculatePriceAndTotalPrice(rate, packageResponse);
        } catch (Exception e) {
            log.error("Unexpected error at createPackage ", e);
            packageService.deletePackage(packageResponse.id());
            throw new CodingExerciseRuntimeException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PackageResponse getPackage(String id, CurrencyCode currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        PackageResponse packageResponse = super.getPackage(id, currencyCode);
        return recalculatePriceAndTotalPrice(rate, packageResponse);
    }

    @Override
    public List<PackageResponse> getPackage(CurrencyCode currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        List<PackageResponse> packageResponses = super.getPackage(currencyCode);
        return packageResponses
                .stream()
                .map(packageResponse -> recalculatePriceAndTotalPrice(rate, packageResponse))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public PackageResponse updatePackage(String id, PackageRequest packageRequest, CurrencyCode currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        PackageResponse packageResponse = super.updatePackage(id, packageRequest, currencyCode);
        return recalculatePriceAndTotalPrice(rate, packageResponse); // due to non-atomic nature, in case recalculatePriceAndTotalPrice fails, the updates will be reflected in the repository, tough user will receive internal server error.
    }

    private static PackageResponse recalculatePriceAndTotalPrice(BigDecimal rate, PackageResponse packageResponse) {
        return new PackageResponse(packageResponse.id(),
                packageResponse.name(),
                packageResponse.description(),
                packageResponse.products()
                        .stream()
                        .map(product -> new Product(product.getId(), product.getName(), product.getPrice().multiply(rate), packageResponse.currencyCode()))
                        .collect(Collectors.toUnmodifiableList()),
                packageResponse.totalPrice().multiply(rate),
                packageResponse.currencyCode());
    }

}
