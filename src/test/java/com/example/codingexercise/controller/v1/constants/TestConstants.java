package com.example.codingexercise.controller.v1.constants;

import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.gateway.dto.outgoing.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

public class TestConstants {

    private TestConstants() {
    }

    public static PackageResponse packageResponse = new PackageResponse("abc", "packname", "packdesc", List.of(new ProductResponse("def", "prod1", BigDecimal.TEN, "USD")), BigDecimal.TEN, "USD");

}
