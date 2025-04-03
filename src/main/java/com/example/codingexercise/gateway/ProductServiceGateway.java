package com.example.codingexercise.gateway;

import com.example.codingexercise.gateway.dto.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductServiceGateway {

    private final RestTemplate restTemplate;

    @Value("${rest.product.url}")
    private String productServiceUrl;

    public Optional<Product> getProduct(String id) {
        try {
            return Optional.of(restTemplate.getForObject(productServiceUrl + "/api/v1/products/{id}", Product.class, id));
        } catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                return Optional.empty();
            }
            log.warn("ProductServiceGateway.getProduct failed for id {}.", id);
            log.warn("ProductServiceGateway.getProduct failed.", e);
            throw e;
        }
    }
}
