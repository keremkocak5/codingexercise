package com.example.codingexercise.controller;

import com.example.codingexercise.model.ProductPackage;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.impl.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @PostMapping
    public ProductPackage create(@RequestBody ProductPackage newProductPackage) {
        return null;
        //return packageRepository.create(newProductPackage.getName(), newProductPackage.getDescription(), newProductPackage.getProductIds());
    }

    @GetMapping(value = "/{id}")
    public ProductPackage get(@PathVariable String id) {
        return packageService.getProductPackage(id);
    }
}
