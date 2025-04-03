package com.example.codingexercise.service.impl;

import com.example.codingexercise.gateway.ProductServiceGateway;
import com.example.codingexercise.gateway.dto.Product;
import com.example.codingexercise.model.ProductPackage;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.IPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageService implements IPackageService {

    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private ProductServiceGateway productServiceGateway;

    @Override
    public ProductPackage getProductPackage(String id) {
        Product x = productServiceGateway.getProduct(id);
        System.out.println(x);
        return null;
    }
}
