package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.enums.ErrorCode;
import com.example.codingexercise.exception.CodingExerciseRuntimeException;
import com.example.codingexercise.gateway.dto.ProductApiResponse;
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
import java.util.Map;
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
        Package newProductPackage = persistPackage(packageRequest, currencyCode);
        return new PackageResponse(newProductPackage.getId(),
                newProductPackage.getName(),
                newProductPackage.getDescription(),
                newProductPackage.getProducts(),
                newProductPackage.getTotalPrice(),
                newProductPackage.getCurrencyCode());
    }

    @Override
    public PackageResponse getProductPackage(String id, @NonNull CurrencyCode currencyCode) {
        Package productPackage = packageRepository.findById(id).orElseThrow(() -> new CodingExerciseRuntimeException(ErrorCode.PACKAGE_NOT_FOUND));
        return getPackageResponse(currencyCode, productPackage);
    }

    @Override
    public List<PackageResponse> getProductPackage(@NonNull CurrencyCode currencyCode) {
        List<Package> productPackages = packageRepository.findAll();
        return productPackages
                .stream()
                .map(pack -> getPackageResponse(currencyCode, pack))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean deletePackage(String id) {
        return packageRepository.deleteById(id);
    }

    private Package persistPackage(PackageRequest packageRequest, CurrencyCode currencyCode) {
        Map<String, ProductApiResponse> productApiResponseMap = productService.getProducts();
        List<Product> products = packageRequest.productIds()
                .stream()
                .map(pack -> productApiResponseMap.computeIfAbsent(pack, s -> {
                    throw new CodingExerciseRuntimeException(ErrorCode.PRODUCT_NOT_FOUND);
                }))
                .map(pack -> new Product(pack.id(), pack.name(), BigDecimal.valueOf(pack.usdPrice()), currencyCode.name()))
                .collect(Collectors.toList());
        BigDecimal totalPrice = products.stream().map(product -> product.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        Package newProductPackage = new Package(UUID.randomUUID().toString(), packageRequest.name(), packageRequest.description(), products, totalPrice, currencyCode.name());
        return packageRepository.save(newProductPackage);
    }

    private PackageResponse getPackageResponse(CurrencyCode currencyCode, Package productPackage) {
        return new PackageResponse(productPackage.getId(),
                productPackage.getName(),
                productPackage.getDescription(),
                productPackage.getProducts(),
                productPackage.getTotalPrice(),
                currencyCode.name());
    }

}
