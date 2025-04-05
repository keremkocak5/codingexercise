package com.example.codingexercise.service;

import com.example.codingexercise.gateway.dto.ProductApiResponse;

import java.util.Map;

public interface IProductService {

    Map<String, ProductApiResponse> getProducts();

}
