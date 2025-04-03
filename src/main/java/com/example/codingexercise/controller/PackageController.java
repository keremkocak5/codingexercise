package com.example.codingexercise.controller;

import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.model.ProductPackage;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.impl.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @PostMapping
    public ProductPackage create(@RequestBody PackageRequest packageRequest) {
        return packageService.createPackage(packageRequest);
    }

    @GetMapping(value = "/{id}")
    public ProductPackage get(@PathVariable String id) {
        return packageService.getProductPackage(id);
    }
}
