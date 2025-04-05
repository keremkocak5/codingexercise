package com.example.codingexercise.service.impl;

import com.example.codingexercise.gateway.ProductServiceGateway;
import com.example.codingexercise.gateway.dto.ProductApiResponse;
import com.example.codingexercise.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductServiceGateway productServiceGateway;

    @Override
    public Map<String, ProductApiResponse> getProducts() {
        return productServiceGateway
                .getProducts()
                .stream()
                .collect(Collectors.toMap(ProductApiResponse::id, Function.identity()));
    }
}
