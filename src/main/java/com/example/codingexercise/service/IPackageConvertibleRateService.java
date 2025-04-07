package com.example.codingexercise.service;

import com.example.codingexercise.controller.v1.dto.incoming.PackageRequest;
import com.example.codingexercise.controller.v1.dto.outgoing.PackageResponse;
import com.example.codingexercise.enums.CurrencyCode;

import java.util.List;
import java.util.UUID;

public interface IPackageConvertibleRateService {

    PackageResponse createPackage(PackageRequest packageRequest, CurrencyCode currencyCode);

    PackageResponse getPackage(UUID id, CurrencyCode currencyCode);

    List<PackageResponse> getPackage(CurrencyCode currencyCode);

    PackageResponse updatePackage(UUID id, PackageRequest packageRequest, CurrencyCode currencyCode);
}
