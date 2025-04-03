package com.example.codingexercise.gateway.dto.outgoing;

import java.util.List;

public record PackageResponse(String id,
                              String name,
                              String description,
                              List<String> productIds) {
}
