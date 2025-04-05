package com.example.codingexercise.service.impl;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.enums.ErrorCode;
import com.example.codingexercise.exception.CodingExerciseRuntimeException;
import com.example.codingexercise.gateway.ProductServiceGateway;
import com.example.codingexercise.gateway.dto.ProductApiResponse;
import com.example.codingexercise.model.Product;
import com.example.codingexercise.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductServiceGateway productServiceGateway;

    @Override
    public List<Product> getProductsFromApiAndValidate(List<String> productIds, CurrencyCode currencyCode) {
        Map<String, ProductApiResponse> productsById = productServiceGateway
                .getAllProducts()
                .stream()
                .collect(Collectors.toMap(ProductApiResponse::id, Function.identity()));
        return productIds
                .stream()
                .map(pack -> productsById.computeIfAbsent(pack, s -> {
                    throw new CodingExerciseRuntimeException(ErrorCode.PRODUCT_NOT_FOUND);
                }))
                .map(pack -> new Product(pack.id(), pack.name(), BigDecimal.valueOf(pack.usdPrice()), currencyCode.name()))
                .collect(Collectors.toList());
    }
}
