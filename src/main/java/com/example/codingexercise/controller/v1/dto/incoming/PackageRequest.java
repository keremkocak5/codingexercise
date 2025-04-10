package com.example.codingexercise.controller.v1.dto.incoming;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

import java.util.LinkedList;

public record PackageRequest(
        @NonNull @NotEmpty(message = "name cannot be empty") @Size(min = 2, max = 100, message = "2-100 characters allowed for name") String name,
        @NonNull @NotEmpty(message = "description cannot be empty") @Size(min = 2, max = 100, message = "2-100 characters allowed for description") String description,
        @NonNull @NotEmpty(message = "product ids should be specified") LinkedList<String> productIds) {
}
