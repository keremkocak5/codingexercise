package com.example.codingexercise.service.impl;

import com.example.codingexercise.gateway.ProductServiceGateway;
import com.example.codingexercise.gateway.dto.Product;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.model.ProductPackage;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.IPackageService;
import com.example.codingexercise.util.Constant;
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
public class PackageService implements IPackageService {

    private final PackageRepository packageRepository;
    private final ProductServiceGateway productServiceGateway;

    @Override
    public PackageResponse createPackage(PackageRequest packageRequest) {
        BigDecimal totalCost = getTotalCost(packageRequest.productIds());
        ProductPackage productPackage = persistPackage(packageRequest);
        return new PackageResponse(productPackage.getId(),
                productPackage.getName(),
                productPackage.getDescription(),
                productPackage.getProductIds(),
                totalCost,
                Constant.USD);
    }

    @Override
    public ProductPackage getProductPackage(String id) {
        return packageRepository.get(id).orElseThrow(() -> new RuntimeException("kerem"));
    }

    @Override
    public List<ProductPackage> getProductPackage() {
        return packageRepository.get();
    }

    @Override
    public boolean deletePackage(String id) {
        return packageRepository.delete(id);
    }

    private ProductPackage persistPackage(PackageRequest packageRequest) {
        return packageRepository.create(packageRequest.name(), packageRequest.description(), packageRequest.productIds());
    }

    private BigDecimal getTotalCost(List<String> productIds) {
        Map<String, Integer> productIdAndUsdPriceMap = productServiceGateway
                .getProducts()
                .stream()
                .collect(Collectors.toMap(Product::id, Product::usdPrice));
        return BigDecimal.valueOf(productIds
                .stream()
                .map(x -> productIdAndUsdPriceMap.get(x))
                .mapToInt(m -> m)
                .sum());
    }
}
