package com.example.codingexercise.repository;

import com.example.codingexercise.model.Package;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PackageRepository {

    private final Map<UUID, Package> database = new ConcurrentHashMap<>();

    public Package saveOrUpdate(Package productPackage) {
        database.put(productPackage.getId(), productPackage);
        return productPackage;
    }

    public Optional<Package> findById(UUID id) {
        return Optional.ofNullable(database.getOrDefault(id, null));
    }

    public List<Package> findAll() {
        return database.values().stream().toList();
    }

    public boolean deleteById(UUID id) {
        return database.remove(id) != null;
    }

}
