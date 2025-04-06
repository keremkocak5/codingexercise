package com.example.codingexercise.constants;

import com.example.codingexercise.gateway.dto.ProductApiResponse;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.gateway.dto.outgoing.ProductResponse;
import com.example.codingexercise.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class TestConstants {

    private TestConstants() {
    }

    public static final PackageResponse packageResponse = new PackageResponse("4eef06bd-c5d2-4a75-9d30-3ac302c59035", "packname", "packdesc", List.of(new ProductResponse("def", "prod1", BigDecimal.TEN, "USD")), BigDecimal.TEN, "USD");
    public static final PackageRequest packageRequest = new PackageRequest("packname", "packdesc", List.of("4eef06bd-c5d2-4a75-9d30-3ac302c59035"));
    public static final Product product1 = new Product("a1", "helmet", BigDecimal.TEN, "USD" );
    public static final Product product2 = new Product("a2", "kindle", BigDecimal.ONE, "USD" );
    public static final ProductApiResponse productApiResponse1 = new ProductApiResponse("a1", "helmet", BigDecimal.TEN);
    public static final ProductApiResponse productApiResponse2 = new ProductApiResponse("a2", "kindle", BigDecimal.ONE);

}
