package com.example.codingexercise.gateway;

import com.example.codingexercise.gateway.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductServiceGateway {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rest.product.url}")
    private String productServiceUrl;

    public Product getProduct(String id) {
        return restTemplate.getForObject(productServiceUrl + "/api/v1/products/{id}", Product.class, id);
    }
}
