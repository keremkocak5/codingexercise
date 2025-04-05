package com.example.codingexercise.service;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class PackageServiceDecorator implements IPackageConvertibleRateService {

    @Autowired
    private final IPackageConvertibleRateService wrappee;

    @Override
    public PackageResponse createPackage(PackageRequest packageRequest, CurrencyCode currencyCode) {
        return wrappee.createPackage(packageRequest, currencyCode);
    }

    @Override
    public PackageResponse getProductPackage(String id, CurrencyCode currencyCode) {
        return wrappee.getProductPackage(id, currencyCode); // kerem
    }

    @Override
    public List<PackageResponse> getProductPackage(CurrencyCode currencyCode) {
        return wrappee.getProductPackage(currencyCode);
    }

}
