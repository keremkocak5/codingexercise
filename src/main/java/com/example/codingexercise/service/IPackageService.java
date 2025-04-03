package com.example.codingexercise.service;

import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.model.ProductPackage;

public interface IPackageService {

    ProductPackage createPackage(PackageRequest packageRequest);

    ProductPackage getProductPackage(String id);

}
