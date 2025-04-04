package com.example.codingexercise.controller;

import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.model.ProductPackage;
import com.example.codingexercise.service.IPackageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/packages")
@RequiredArgsConstructor
public class PackageController {

    private final IPackageService packageService;

    @PostMapping
    @Operation(summary = "kerem")
    public ProductPackage create(@RequestBody PackageRequest packageRequest) {
        return packageService.createPackage(packageRequest);
    }

    @GetMapping(value = "/{id}")
    public ProductPackage get(@PathVariable String id) {
        return packageService.getProductPackage(id);
    }

    @GetMapping
    public List<ProductPackage> get() {
        return packageService.getProductPackage();
    }

    @DeleteMapping(value = "/{id}")
    public boolean delete(@PathVariable String id) {
        return packageService.deletePackage(id);
    }

}
