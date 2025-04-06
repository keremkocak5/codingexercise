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

import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductServiceGateway productServiceGateway;

    @Override
    public LinkedList<Product> getProductDetailsFromApiAndValidate(LinkedList<String> productIds, CurrencyCode currencyCode) {
        Map<String, ProductApiResponse> productsById = productServiceGateway
                .getAllProducts()
                .stream()
                .collect(Collectors.toMap(ProductApiResponse::id, Function.identity()));
        return productIds
                .stream()
                .map(id -> productsById.computeIfAbsent(id, s -> {
                    throw new CodingExerciseRuntimeException(ErrorCode.PRODUCT_NOT_FOUND);
                }))
                .map(aProduct -> new Product(aProduct.id(), aProduct.name(), aProduct.usdPrice(), currencyCode.name()))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
