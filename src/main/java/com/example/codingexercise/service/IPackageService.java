package com.example.codingexercise.service;

import com.example.codingexercise.enums.CurrencyCodeEnum;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.model.ProductPackage;

import java.util.List;

public interface IPackageService {

    PackageResponse createPackage(PackageRequest packageRequest, CurrencyCodeEnum currencyCodeEnum);

    PackageResponse getProductPackage(String id, CurrencyCodeEnum currencyCodeEnum);

    List<PackageResponse> getProductPackage(CurrencyCodeEnum currencyCodeEnum);

    boolean deletePackage(String id); // ayri olabilir
}
