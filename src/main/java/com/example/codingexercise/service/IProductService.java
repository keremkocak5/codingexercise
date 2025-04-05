package com.example.codingexercise.service;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.model.Product;

import java.util.List;

public interface IProductService {

    List<Product> getProductsFromApiAndValidate(PackageRequest packageRequest, CurrencyCode currencyCode);

}
