package com.example.codingexercise.service.impl;

import com.example.codingexercise.constants.TestConstants;
import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.exception.CodingExerciseRuntimeException;
import com.example.codingexercise.gateway.ProductServiceGateway;
import com.example.codingexercise.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductServiceGateway productServiceGateway;

    @Test
    void getProductDetailsFromApiAndValidateShouldReturnProductMapAllProductsFound() {
        when(productServiceGateway.getAllProducts()).thenReturn(List.of(TestConstants.productApiResponse1, TestConstants.productApiResponse2));

        List<Product> result = productService.getProductDetailsFromApiAndValidate(new LinkedList<String>(List.of("a1", "a2")), CurrencyCode.USD, UUID.fromString("4eef06bd-c5d2-4a75-9d30-3ac302c59035"));

        assertThat(result, hasSize(2));
        assertThat(result.get(0), is(TestConstants.product1));
        assertThat(result.get(1), is(TestConstants.product2));
    }

    @Test
    void getProductDetailsFromApiAndValidateShouldThrowExceptionWhenProductNotFound() {
        when(productServiceGateway.getAllProducts()).thenReturn(List.of(TestConstants.productApiResponse1, TestConstants.productApiResponse2));

        assertThrows(CodingExerciseRuntimeException.class, () -> productService.getProductDetailsFromApiAndValidate(new LinkedList<>(List.of("a1", "a2", "a3")), CurrencyCode.USD, UUID.fromString("4eef06bd-c5d2-4a75-9d30-3ac302c59035")));
    }

}