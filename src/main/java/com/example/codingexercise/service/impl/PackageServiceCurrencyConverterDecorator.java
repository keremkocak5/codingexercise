package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCodeEnum;
import com.example.codingexercise.gateway.FrankfurterServiceGateway;
import com.example.codingexercise.gateway.dto.Rate;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.model.ProductPackage;
import com.example.codingexercise.service.IPackageService;
import com.example.codingexercise.service.PackageServiceDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageServiceCurrencyConverterDecorator extends PackageServiceDecorator {

    public PackageServiceCurrencyConverterDecorator(IPackageService wrappee) {
        super(wrappee);
    }

    @Autowired
    private FrankfurterServiceGateway frankfurterServiceGateway;

    @Override
    public PackageResponse createPackage(PackageRequest packageRequest) {
        Rate m = frankfurterServiceGateway.getCurrencyBaseUsd(CurrencyCodeEnum.AUD);
        return super.createPackage(packageRequest);
    }

    @Override
    public ProductPackage getProductPackage(String id) {
        return null;
    }

    @Override
    public List<ProductPackage> getProductPackage() {
        return null;
    }

    @Override
    public boolean deletePackage(String id) {
        return super.deletePackage(id);
    }

}
