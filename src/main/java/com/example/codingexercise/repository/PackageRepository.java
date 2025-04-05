package com.example.codingexercise.repository;

import com.example.codingexercise.model.ProductPackage;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PackageRepository {

    private volatile Map<String, ProductPackage> productPackages = new Hashtable<>();

    public ProductPackage save(ProductPackage productPackage) {
        productPackages.put(productPackage.getId(), productPackage);
        return productPackage;
    }

    public Optional<ProductPackage> findById(String id) {
        return Optional.ofNullable(productPackages.getOrDefault(id, null));
    }

    public List<ProductPackage> findAll() {
        return productPackages.values().stream().toList();
    }

    public boolean deleteById(String id) {
        return productPackages.remove(id) != null;
    }

}
