package com.example.codingexercise.constants;

import com.example.codingexercise.controller.v1.dto.incoming.PackageRequest;
import com.example.codingexercise.controller.v1.dto.outgoing.PackageResponse;
import com.example.codingexercise.controller.v1.dto.outgoing.ProductResponse;
import com.example.codingexercise.gateway.dto.ProductApiResponse;
import com.example.codingexercise.model.Product;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TestConstants {

    private TestConstants() {
    }

    public static final PackageResponse packageResponse = new PackageResponse(UUID.fromString("4eef06bd-c5d2-4a75-9d30-3ac302c59035"), "packname", "packdesc", List.of(new ProductResponse("def", "prod1", BigDecimal.TEN, "USD")), BigDecimal.TEN, "USD");
    public static final PackageRequest packageRequest = new PackageRequest("packname", "packdesc", new LinkedList<String>(List.of("4eef06bd-c5d2-4a75-9d30-3ac302c59035")));
    public static final Product product1 = new Product("a1", UUID.fromString("4eef06bd-c5d2-4a75-9d30-3ac302c59035"), "helmet", BigDecimal.TEN, "USD");
    public static final Product product2 = new Product("a2", UUID.fromString("4eef06bd-c5d2-4a75-9d30-3ac302c59035"), "kindle", BigDecimal.ONE, "USD");
    public static final ProductApiResponse productApiResponse1 = new ProductApiResponse("a1", "helmet", BigDecimal.TEN);
    public static final ProductApiResponse productApiResponse2 = new ProductApiResponse("a2", "kindle", BigDecimal.ONE);
    public static final com.example.codingexercise.model.Package package1 = new com.example.codingexercise.model.Package(UUID.fromString("4eef06bd-c5d2-4a75-9d30-3ac302c59035"), "oldname", "olddesc", new LinkedList<>(List.of(TestConstants.product1)), BigDecimal.TEN, "USD");
}
