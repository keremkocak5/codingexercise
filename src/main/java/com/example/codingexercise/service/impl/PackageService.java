package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.enums.ErrorCode;
import com.example.codingexercise.exception.CodingExerciseRuntimeException;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.model.Package;
import com.example.codingexercise.model.Product;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.IPackageConvertibleRateService;
import com.example.codingexercise.service.IPackageService;
import com.example.codingexercise.service.IProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class PackageService implements IPackageConvertibleRateService, IPackageService {

    private final PackageRepository packageRepository;
    private final IProductService productService;

    @Override
    public PackageResponse createPackage(@NonNull PackageRequest packageRequest, @NonNull CurrencyCode currencyCode) {
        Package newPackage = getProductsAndSavePackage(packageRequest, currencyCode);
        return new PackageResponse(newPackage.getId(),
                newPackage.getName(),
                newPackage.getDescription(),
                newPackage.getProducts(),
                newPackage.getTotalPrice(),
                newPackage.getCurrencyCode());
    }

    @Override
    public PackageResponse getPackage(@NonNull String id, @NonNull CurrencyCode currencyCode) {
        Package aPackage = getPackageOrThrowIfNotFound(id);
        return createPackageResponse(currencyCode, aPackage);
    }

    @Override
    public List<PackageResponse> getPackage(@NonNull CurrencyCode currencyCode) {
        List<Package> productPackages = packageRepository.findAll();
        return productPackages
                .stream()
                .map(aPackage -> createPackageResponse(currencyCode, aPackage))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public PackageResponse updatePackage(@NonNull String id, @NonNull PackageRequest packageRequest, @NonNull CurrencyCode currencyCode) {
        Package updatedPackage = getProductsAndUpdatePackage(id, packageRequest, currencyCode);
        return new PackageResponse(updatedPackage.getId(),
                updatedPackage.getName(),
                updatedPackage.getDescription(),
                updatedPackage.getProducts(),
                updatedPackage.getTotalPrice(),
                updatedPackage.getCurrencyCode());
    }

    @Override
    public boolean deletePackage(@NonNull String id) {
        return packageRepository.deleteById(id);
    }

    private Package getProductsAndSavePackage(PackageRequest packageRequest, CurrencyCode currencyCode) {
        List<Product> products = productService.getProductDetailsFromApiAndValidate(packageRequest.productIds(), currencyCode);
        BigDecimal totalPrice = getTotalPrice(products);
        Package newPackage = new Package(UUID.randomUUID().toString(), packageRequest.name(), packageRequest.description(), products, totalPrice, currencyCode.name());
        return packageRepository.saveOrUpdate(newPackage);
    }

    private Package getProductsAndUpdatePackage(String id, PackageRequest packageRequest, CurrencyCode currencyCode) {
        List<String> mergedProductIds = mergeExistingAndNewProductIds(id, packageRequest);
        List<Product> products = productService.getProductDetailsFromApiAndValidate(mergedProductIds, currencyCode);
        BigDecimal totalPrice = getTotalPrice(products);
        Package existingPackage = new Package(id, packageRequest.name(), packageRequest.description(), products, totalPrice, currencyCode.name());
        return packageRepository.saveOrUpdate(existingPackage);
    }

    private List<String> mergeExistingAndNewProductIds(String id, PackageRequest packageRequest) {
        List<String> productIds = getPackageOrThrowIfNotFound(id).getProducts().stream().map(x -> x.getId()).collect(Collectors.toList());
        productIds.addAll(packageRequest.productIds());
        return productIds;
    }

    private static BigDecimal getTotalPrice(List<Product> products) {
        return products.stream().map(product -> product.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Package getPackageOrThrowIfNotFound(String id) {
        return packageRepository.findById(id).orElseThrow(() -> new CodingExerciseRuntimeException(ErrorCode.PACKAGE_NOT_FOUND));
    }

    private PackageResponse createPackageResponse(CurrencyCode currencyCode, Package productPackage) {
        return new PackageResponse(productPackage.getId(),
                productPackage.getName(),
                productPackage.getDescription(),
                productPackage.getProducts(),
                productPackage.getTotalPrice(),
                currencyCode.name());
    }

}
