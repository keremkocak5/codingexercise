package com.example.codingexercise.service.impl;

import com.example.codingexercise.gateway.ProductServiceGateway;
import com.example.codingexercise.gateway.dto.Product;
import com.example.codingexercise.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductServiceGateway productServiceGateway;

    @Override
    public Map<String, Integer> getProductIdAndUsdPrice() {
        return productServiceGateway
                .getProducts()
                .stream()
                .collect(Collectors.toMap(Product::id, Product::usdPrice));
    }
}
