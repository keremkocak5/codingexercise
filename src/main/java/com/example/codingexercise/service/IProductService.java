package com.example.codingexercise.service;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.model.Product;

import java.util.List;

public interface IProductService {

    List<Product> getProductDetailsFromApiAndValidate(List<String> productIds, CurrencyCode currencyCode);

}
