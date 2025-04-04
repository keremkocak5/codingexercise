package com.example.codingexercise.service.impl;

import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.model.ProductPackage;
import com.example.codingexercise.service.IPackageService;
import com.example.codingexercise.service.PackageServiceDecorator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PackageServiceCurrencyConverterDecorator extends PackageServiceDecorator {

    public PackageServiceCurrencyConverterDecorator(IPackageService wrappee) {
        super(wrappee);
    }

    @Override
    public PackageResponse createPackage(PackageRequest packageRequest) {
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
