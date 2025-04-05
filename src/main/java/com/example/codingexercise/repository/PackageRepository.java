package com.example.codingexercise.repository;

import com.example.codingexercise.model.Package;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PackageRepository {

    private volatile Map<String, Package> database = new ConcurrentHashMap<>();

    public Package save(Package productPackage) {
        database.put(productPackage.getId(), productPackage);
        return productPackage;
    }

    public Optional<Package> findById(String id) {
        return Optional.ofNullable(database.getOrDefault(id, null));
    }

    public List<Package> findAll() {
        return database.values().stream().toList();
    }

    public boolean deleteById(String id) {
        return database.remove(id) != null;
    }

}
