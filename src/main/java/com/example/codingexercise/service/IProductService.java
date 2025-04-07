package com.example.codingexercise.service;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.model.Product;

import java.util.LinkedList;
import java.util.UUID;

public interface IProductService {

    LinkedList<Product> getProductDetailsFromApiAndValidate(LinkedList<String> productIds, CurrencyCode currencyCode, UUID packageId);

}
