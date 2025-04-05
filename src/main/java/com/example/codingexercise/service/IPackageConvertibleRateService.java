package com.example.codingexercise.service;

import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;

import java.util.List;

public interface IPackageConvertibleRateService {

    PackageResponse createPackage(PackageRequest packageRequest, CurrencyCode currencyCode);

    PackageResponse getPackage(String id, CurrencyCode currencyCode);

    List<PackageResponse> getPackage(CurrencyCode currencyCode);

}
