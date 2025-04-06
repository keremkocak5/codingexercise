package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.enums.ErrorCode;
import com.example.codingexercise.exception.CodingExerciseRuntimeException;
import com.example.codingexercise.controller.v1.dto.incoming.PackageRequest;
import com.example.codingexercise.controller.v1.dto.outgoing.PackageResponse;
import com.example.codingexercise.controller.v1.dto.outgoing.ProductResponse;
import com.example.codingexercise.service.ICurrencyService;
import com.example.codingexercise.service.IPackageConvertibleRateService;
import com.example.codingexercise.service.IPackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PackageServiceBaseUsdCurrencyConverterDecorator implements IPackageConvertibleRateService {

    private final ICurrencyService currencyService;
    private final IPackageService packageService;
    private final IPackageConvertibleRateService packageConvertibleRateService;

    @Override
    public PackageResponse createPackage(PackageRequest packageRequest, CurrencyCode currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        PackageResponse packageResponse = packageConvertibleRateService.createPackage(packageRequest, currencyCode);
        try {
            return recalculatePriceAndTotalPrice(rate, packageResponse);
        } catch (Exception e) {
            log.error("Unexpected error at createPackage ", e);
            packageService.deletePackage(packageResponse.id()); // simulating rollback
            throw new CodingExerciseRuntimeException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PackageResponse getPackage(UUID id, CurrencyCode currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        PackageResponse packageResponse = packageConvertibleRateService.getPackage(id, currencyCode);
        return recalculatePriceAndTotalPrice(rate, packageResponse);
    }

    @Override
    public List<PackageResponse> getPackage(CurrencyCode currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        List<PackageResponse> packageResponses = packageConvertibleRateService.getPackage(currencyCode);
        return packageResponses
                .stream()
                .map(packageResponse -> recalculatePriceAndTotalPrice(rate, packageResponse))
                .toList();
    }

    @Override
    public PackageResponse updatePackage(UUID id, PackageRequest packageRequest, CurrencyCode currencyCode) {
        BigDecimal rate = currencyService.getCurrencyBaseUsd(currencyCode);
        PackageResponse packageResponse = packageConvertibleRateService.updatePackage(id, packageRequest, currencyCode);
        return recalculatePriceAndTotalPrice(rate, packageResponse); // due to non-atomic nature, in case recalculatePriceAndTotalPrice fails, the updates will be reflected in the repository, tough user will receive internal server error.
    }

    private static PackageResponse recalculatePriceAndTotalPrice(BigDecimal rate, PackageResponse packageResponse) {
        return new PackageResponse(packageResponse.id(),
                packageResponse.name(),
                packageResponse.description(),
                packageResponse.products()
                        .stream()
                        .map(product -> new ProductResponse(product.id(), product.name(), product.price().multiply(rate), packageResponse.currencyCode()))
                        .toList(),
                packageResponse.totalPrice().multiply(rate),
                packageResponse.currencyCode());
    }

}
