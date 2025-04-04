package com.example.codingexercise.controller.v1;

import com.example.codingexercise.enums.CurrencyCodeEnum;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.model.ProductPackage;
import com.example.codingexercise.service.IPackageService;
import com.example.codingexercise.service.impl.PackageServiceCurrencyConverterDecorator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/packages")
@RequiredArgsConstructor
public class PackageController {

    private final IPackageService packageService;
    private final PackageServiceCurrencyConverterDecorator packageService2;

    @PostMapping(value = {"/currency/{optionalCurrency}", "/"})
    @Operation(summary = "Create package")
    public PackageResponse create(@RequestBody PackageRequest packageRequest, @PathVariable(name = "optionalCurrency", required = false) Optional<CurrencyCodeEnum> optionalCurrency) {
        return packageService2.createPackage(packageRequest);
    }

    @GetMapping(value = {"/id/{id}/currency/{optionalCurrency}","/id/{id}"})
    @Operation(summary = "Get package")
    public ProductPackage get(@PathVariable String id, @PathVariable(name = "optionalCurrency", required = false) Optional<CurrencyCodeEnum> optionalCurrency) {
        return packageService.getProductPackage(id);
    }

    @GetMapping(value = {"/currency/{optionalCurrency}", "/"})
    @Operation(summary = "Get all packages")
    public List<ProductPackage> get(@PathVariable(name = "optionalCurrency", required = false) Optional<CurrencyCodeEnum> optionalCurrency) {
        return packageService.getProductPackage();
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete package")
    public boolean delete(@PathVariable String id) {
        return packageService.deletePackage(id);
    }

}
