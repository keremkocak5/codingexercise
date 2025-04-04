package com.example.codingexercise.service.impl;

import com.example.codingexercise.gateway.ProductServiceGateway;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.model.ProductPackage;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.IPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageService implements IPackageService {

    private final PackageRepository packageRepository;
    private final ProductServiceGateway productServiceGateway;

    @Override
    public ProductPackage createPackage(PackageRequest packageRequest) {
        return packageRepository.create(packageRequest.name(), packageRequest.description(), packageRequest.productIds());
    }

    @Override
    public ProductPackage getProductPackage(String id) {
        return packageRepository.get(id)
                .get(); // kerem
    }

    @Override
    public List<ProductPackage> getProductPackage() {
        return packageRepository.get();
    }

    @Override
    public boolean deletePackage(String id) {
        return packageRepository.delete(id);
    }
}
