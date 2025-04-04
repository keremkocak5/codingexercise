package com.example.codingexercise.service;

import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.model.ProductPackage;
import com.example.codingexercise.service.IPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class PackageServiceDecorator implements IPackageService {

    @Autowired
    private final IPackageService wrappee;

    @Override
    public PackageResponse createPackage(PackageRequest packageRequest) {
        return wrappee.createPackage(packageRequest);
    }

    @Override
    public ProductPackage getProductPackage(String id) {
        return wrappee.getProductPackage(id); // kerem
    }

    @Override
    public List<ProductPackage> getProductPackage() {
        return wrappee.getProductPackage();
    }

    @Override
    public boolean deletePackage(String id) {
        return wrappee.deletePackage(id);
    }
}
