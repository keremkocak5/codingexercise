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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class PackageService implements IPackageConvertibleRateService, IPackageService {

    private final PackageRepository packageRepository;
    private final IProductService productService;

    @Override
    public PackageResponse createPackage(@NonNull PackageRequest packageRequest, @NonNull CurrencyCodeEnum currencyCode) {
        BigDecimal totalPrice = validateProductsAndFindTotalPrice(packageRequest.productIds());
        ProductPackage productPackage = persistPackage(packageRequest, totalPrice, currencyCode);
        return new PackageResponse(productPackage.getId(),
                productPackage.getName(),
                productPackage.getDescription(),
                productPackage.getProductIds(),
                productPackage.getTotalPrice(),
                productPackage.getCurrencyCode());
    }

    @Override
    public PackageResponse getProductPackage(String id, @NonNull CurrencyCodeEnum currencyCodeEnum) {
        ProductPackage productPackage = packageRepository.findById(id).orElseThrow(() -> new RuntimeException("kerem"));
        return getPackageResponse(currencyCodeEnum, productPackage);
    }

    @Override
    public List<PackageResponse> getProductPackage(@NonNull CurrencyCodeEnum currencyCodeEnum) {
        List<ProductPackage> productPackages = packageRepository.findAll();
        return productPackages
                .stream()
                .map(pack -> getPackageResponse(currencyCodeEnum, pack))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean deletePackage(String id) {
        return packageRepository.deleteById(id);
    }

    private ProductPackage persistPackage(PackageRequest packageRequest, BigDecimal totalPrice, CurrencyCodeEnum currencyCode) {
        ProductPackage newProductPackage = new ProductPackage(UUID.randomUUID().toString(), packageRequest.name(), packageRequest.description(), packageRequest.productIds(), totalPrice, currencyCode.name());
        return packageRepository.save(newProductPackage);
    }

    private PackageResponse getPackageResponse(CurrencyCodeEnum currencyCodeEnum, ProductPackage productPackage) {
        return new PackageResponse(productPackage.getId(),
                productPackage.getName(),
                productPackage.getDescription(),
                productPackage.getProductIds(),
                productPackage.getTotalPrice(),
                currencyCodeEnum.name());
    }

    private BigDecimal validateProductsAndFindTotalPrice(List<String> productIds) {
        Map<String, Integer> productIdAndUsdPrices = productService.getProductIdAndUsdPrice();
        return BigDecimal.valueOf(productIds
                .stream()
                .map(productId -> productIdAndUsdPrices.get(productId))
                .mapToInt(price -> price)
                .sum());
    }

}
