package com.example.codingexercise.gateway;

import com.example.codingexercise.gateway.dto.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductServiceGateway {

    private final RestTemplate productRestTemplate;

    @Value("${rest.product.url}")
    private String productServiceUrl;

    public Optional<Product> getProduct(String id) {
        try {
            return Optional.of(productRestTemplate.getForObject(productServiceUrl + "/api/v1/products/{id}", Product.class, id));
        } catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                return Optional.empty();
            }
            log.error("ProductServiceGateway.getProduct failed for id {}.", id);
            log.error("ProductServiceGateway.getProduct failed.", e);
            throw e;
        }
    }

    public List<Product> getProducts() {
        try {
            String response = productRestTemplate.getForObject(productServiceUrl + "/api/v1/products", String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, new TypeReference<List<Product>>() {
            });
        } catch (HttpClientErrorException e) {
            log.error("ProductServiceGateway.getProducts failed.", e);
            throw e;
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
