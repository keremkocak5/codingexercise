package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCodeEnum;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.model.ProductPackage;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class PackageRateService implements IPackageConvertibleRateService, IPackageService {

    private final PackageRepository packageRepository;
    private final IProductService productService;

    @Override
    public PackageResponse createPackage(@NonNull PackageRequest packageRequest, @NonNull CurrencyCodeEnum currencyCodeEnum) {
        Map<String, Integer> productIdAndUsdPrice = productService.getProductIdAndUsdPrice();
        BigDecimal totalCost = validateProductsAndFindTotalCost(packageRequest.productIds(), productIdAndUsdPrice);
        ProductPackage productPackage = persistPackage(packageRequest);
        return new PackageResponse(productPackage.getId(),
                productPackage.getName(),
                productPackage.getDescription(),
                productPackage.getProductIds(),
                totalCost,
                currencyCodeEnum.name());
    }

    @Override
    public PackageResponse getProductPackage(String id, @NonNull CurrencyCodeEnum currencyCodeEnum) {
        ProductPackage productPackage = packageRepository.get(id).orElseThrow(() -> new RuntimeException("kerem"));
        Map<String, Integer> productIdAndUsdPrice = productService.getProductIdAndUsdPrice();
        return getPackageResponse(currencyCodeEnum, productPackage, productIdAndUsdPrice);
    }

    @Override
    public List<PackageResponse> getProductPackage(@NonNull CurrencyCodeEnum currencyCodeEnum) {
        List<ProductPackage> productPackages = packageRepository.get();
        Map<String, Integer> productIdAndUsdPrice = productService.getProductIdAndUsdPrice();
        return productPackages
                .stream()
                .map(pack -> getPackageResponse(currencyCodeEnum, pack, productIdAndUsdPrice))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean deletePackage(String id) {
        return packageRepository.delete(id);
    }

    private ProductPackage persistPackage(PackageRequest packageRequest) {
        return packageRepository.create(packageRequest.name(), packageRequest.description(), packageRequest.productIds());
    }

    private PackageResponse getPackageResponse(CurrencyCodeEnum currencyCodeEnum, ProductPackage productPackage, Map<String, Integer> productIdAndUsdPrice) {
        BigDecimal totalCost = validateProductsAndFindTotalCost(productPackage.getProductIds(), productIdAndUsdPrice);
        return new PackageResponse(productPackage.getId(),
                productPackage.getName(),
                productPackage.getDescription(),
                productPackage.getProductIds(),
                totalCost,
                currencyCodeEnum.name());
    }

    private BigDecimal validateProductsAndFindTotalCost(List<String> productIds, Map<String, Integer> productIdAndUsdPrice) {
        return BigDecimal.valueOf(productIds
                .stream()
                .map(productId -> productIdAndUsdPrice.get(productId))
                .mapToInt(cost -> cost)
                .sum());
    }

}
