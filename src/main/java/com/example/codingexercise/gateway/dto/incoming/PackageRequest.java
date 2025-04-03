package com.example.codingexercise.gateway.dto.incoming;

import java.util.List;

public record PackageRequest(String name,
                             String description,
                             List<String> productIds) {
}
