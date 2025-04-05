package com.example.codingexercise.service;

import com.example.codingexercise.enums.CurrencyCodeEnum;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.model.ProductPackage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class PackageServiceDecorator implements IPackageService {

    @Autowired
    private final IPackageService wrappee;

    @Override
    public PackageResponse createPackage(PackageRequest packageRequest, CurrencyCodeEnum currencyCodeEnum) {
        return wrappee.createPackage(packageRequest, currencyCodeEnum);
    }

    @Override
    public PackageResponse getProductPackage(String id, CurrencyCodeEnum currencyCodeEnum) {
        return wrappee.getProductPackage(id, currencyCodeEnum); // kerem
    }

    @Override
    public List<PackageResponse> getProductPackage(CurrencyCodeEnum currencyCodeEnum) {
        return wrappee.getProductPackage(currencyCodeEnum);
    }

    @Override
    public boolean deletePackage(String id) {
        return wrappee.deletePackage(id);
    }
}
