package com.example.codingexercise.repository;

import com.example.codingexercise.model.ProductPackage;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PackageRepository {

    private final Map<String, ProductPackage> productPackages = new HashMap<>();

    public ProductPackage create(String name, String description, List<String> productIds) {
        ProductPackage newProductPackage = new ProductPackage(UUID.randomUUID().toString(), name, description, productIds);
        productPackages.put(newProductPackage.getId(), newProductPackage);
        return newProductPackage;
    }

    public Optional<ProductPackage> get(String id) {
        return Optional.ofNullable(productPackages.getOrDefault(id, null));
    }

}
