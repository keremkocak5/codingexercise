package com.example.codingexercise.gateway.dto.incoming;

import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;

import java.util.List;

public record PackageRequest(@NonNull @NotEmpty String name,
                             String description,
                             List<String> productIds) {
}
