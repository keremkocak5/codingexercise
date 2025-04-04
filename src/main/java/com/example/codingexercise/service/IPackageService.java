package com.example.codingexercise.service;

import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.model.ProductPackage;

import java.util.List;

public interface IPackageService {

    PackageResponse createPackage(PackageRequest packageRequest);

    ProductPackage getProductPackage(String id);

    List<ProductPackage> getProductPackage();

    boolean deletePackage(String id); // ayri olabilir
}
