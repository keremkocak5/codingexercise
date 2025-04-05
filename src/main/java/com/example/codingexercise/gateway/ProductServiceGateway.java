package com.example.codingexercise.gateway;

import com.example.codingexercise.enums.ErrorCode;
import com.example.codingexercise.exception.CodingExerciseRuntimeException;
import com.example.codingexercise.gateway.dto.ProductApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceGateway {

    private final RestTemplate productRestTemplate;

    @Value("${rest.product.url}")
    private String productServiceUrl;

    public List<ProductApiResponse> getProducts() {
        try {
            String response = productRestTemplate.getForObject(productServiceUrl + "/api/v1/products", String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, new TypeReference<List<ProductApiResponse>>() {
            });
        } catch (Exception e) {
            log.error("ProductServiceGateway.getProducts failed.", e);
            throw new CodingExerciseRuntimeException(ErrorCode.PACKAGE_CONNECTION_ERROR);
        }
    }
}
