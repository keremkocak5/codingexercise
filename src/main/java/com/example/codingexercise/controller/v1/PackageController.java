package com.example.codingexercise.controller.v1;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.service.IPackageService;
import com.example.codingexercise.service.impl.PackageServiceFactory;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/packages")
@RequiredArgsConstructor
@Validated
public class PackageController {

    private final PackageServiceFactory packageServiceFactory;
    private final IPackageService packageService;

    @PostMapping(value = {"/currency/{currency}", "/"})
    @Operation(summary = "Create package")
    public PackageResponse create(@Valid @RequestBody PackageRequest packageRequest, @PathVariable(name = "currency", required = false) Optional<CurrencyCode> currency) {
        return packageServiceFactory.getPackageService(currency).createPackage(packageRequest, getCurrencyDefaultUsd(currency));
    }

    @GetMapping(value = {"/id/{id}/currency/{currency}", "/id/{id}"})
    @Operation(summary = "Get package")
    public PackageResponse get(@PathVariable String id, @PathVariable(name = "currency", required = false) Optional<CurrencyCode> currency) {
        return packageServiceFactory.getPackageService(currency).getPackage(id, getCurrencyDefaultUsd(currency));
    }

    @GetMapping(value = {"/currency/{currency}", "/"})
    @Operation(summary = "Get all packages")
    public List<PackageResponse> get(@PathVariable(name = "currency", required = false) Optional<CurrencyCode> currency) {
        return packageServiceFactory.getPackageService(currency).getPackage(getCurrencyDefaultUsd(currency));
    }

    @PostMapping(value = {"/id/{id}/currency/{currency}", "/id/{id}"})
    @Operation(summary = "Update package")
    public PackageResponse update(@PathVariable String id, @Valid @RequestBody PackageRequest packageRequest, @PathVariable(name = "currency", required = false) Optional<CurrencyCode> currency) {
        return packageServiceFactory.getPackageService(currency).updatePackage(id, packageRequest, getCurrencyDefaultUsd(currency));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete package")
    public boolean delete(@PathVariable String id) {
        return packageService.deletePackage(id);
    }

    private static CurrencyCode getCurrencyDefaultUsd(Optional<CurrencyCode> currency) {
        return currency.orElse(CurrencyCode.USD);
    }

}
