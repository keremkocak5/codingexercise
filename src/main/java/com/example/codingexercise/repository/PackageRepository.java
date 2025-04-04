package com.example.codingexercise.repository;

import com.example.codingexercise.model.ProductPackage;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PackageRepository {

    private volatile Map<String, ProductPackage> productPackages = new Hashtable<>();

    public ProductPackage create(String name, String description, List<String> productIds) {
        ProductPackage newProductPackage = new ProductPackage(UUID.randomUUID().toString(), name, description, productIds);
        productPackages.put(newProductPackage.getId(), newProductPackage);
        return newProductPackage;
    }

    public Optional<ProductPackage> get(String id) {
        return Optional.ofNullable(productPackages.getOrDefault(id, null));
    }

    public List<ProductPackage> get() {
        return productPackages.values().stream().toList();
    }

    public boolean delete(String id) {
        return productPackages.remove(id) != null;
    }


}
