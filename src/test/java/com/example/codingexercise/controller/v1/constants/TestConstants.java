package com.example.codingexercise.controller.v1.constants;

import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.gateway.dto.outgoing.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

public class TestConstants {

    private TestConstants() {
    }

    public static final PackageResponse packageResponse = new PackageResponse("4eef06bd-c5d2-4a75-9d30-3ac302c59035", "packname", "packdesc", List.of(new ProductResponse("def", "prod1", BigDecimal.TEN, "USD")), BigDecimal.TEN, "USD");
    public static final PackageRequest packageRequest = new PackageRequest("packname", "packdesc", List.of("4eef06bd-c5d2-4a75-9d30-3ac302c59035"));
}
