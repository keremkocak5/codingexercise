package com.example.codingexercise.service.impl;

import com.example.codingexercise.constants.TestConstants;
import com.example.codingexercise.controller.v1.dto.incoming.PackageRequest;
import com.example.codingexercise.controller.v1.dto.outgoing.PackageResponse;
import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.model.Package;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.IProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PackageServiceTest {

    @InjectMocks
    private PackageService packageService;

    @Spy
    private PackageRepository packageRepository;
    @Mock
    private IProductService productService;

    @Test
    void createPackageShouldReturnPackageResponseWhenProductsValid() {
        PackageRequest packageRequest = new PackageRequest("packname", "desc", new LinkedList(List.of("a1", "a2")));
        when(productService.getProductDetailsFromApiAndValidate(new LinkedList(List.of("a1", "a2")), CurrencyCode.USD)).thenReturn(new LinkedList(List.of(TestConstants.product1, TestConstants.product2)));

        PackageResponse result = packageService.createPackage(packageRequest, CurrencyCode.USD);

        assertNotNull(result);
        assertNotNull(result.id());
        assertThat(result.currencyCode(), is("USD"));
        assertThat(result.name(), is("packname"));
        assertThat(result.description(), is("desc"));
        assertThat(result.totalPrice(), is(BigDecimal.valueOf(11)));
        assertThat(result.products().size(), is(2));
        assertThat(result.products().get(0).id(), is("a1"));
        assertThat(result.products().get(0).name(), is("helmet"));
        assertThat(result.products().get(0).currencyCode(), is("USD"));
        assertThat(result.products().get(0).price(), is(BigDecimal.TEN));
        assertThat(result.products().get(1).id(), is("a2"));
        assertThat(result.products().get(1).name(), is("kindle"));
        assertThat(result.products().get(1).currencyCode(), is("USD"));
        assertThat(result.products().get(1).price(), is(BigDecimal.ONE));
    }

    @Test
    void updatePackageShouldReturnPackageResponseWhenPackagesValidAndPackageFound() {
        PackageRequest packageRequest = new PackageRequest("packname2", "desc2", new LinkedList(List.of("a1", "a2")));
        when(productService.getProductDetailsFromApiAndValidate(new LinkedList(List.of("a1", "a1", "a2")), CurrencyCode.USD)).thenReturn(new LinkedList(List.of(TestConstants.product1, TestConstants.product2)));
        when(packageRepository.findById(UUID.fromString("4eef06bd-c5d2-4a75-9d30-3ac302c59035"))).thenReturn(Optional.of(TestConstants.package1));

        PackageResponse result = packageService.updatePackage(UUID.fromString("4eef06bd-c5d2-4a75-9d30-3ac302c59035"), packageRequest, CurrencyCode.USD);

        assertNotNull(result);
        assertNotNull(result.id());
        assertThat(result.currencyCode(), is("USD"));
        assertThat(result.name(), is("packname2"));
        assertThat(result.description(), is("desc2"));
        assertThat(result.totalPrice(), is(BigDecimal.valueOf(11)));
        assertThat(result.products().size(), is(2));
        assertThat(result.products().get(0).id(), is("a1"));
        assertThat(result.products().get(0).name(), is("helmet"));
        assertThat(result.products().get(0).currencyCode(), is("USD"));
        assertThat(result.products().get(0).price(), is(BigDecimal.TEN));
        assertThat(result.products().get(1).id(), is("a2"));
        assertThat(result.products().get(1).name(), is("kindle"));
        assertThat(result.products().get(1).currencyCode(), is("USD"));
        assertThat(result.products().get(1).price(), is(BigDecimal.ONE));
    }

    @Test
    void deleteShouldReturnBooleanWhenPackageFound() {
        when(packageRepository.deleteById(UUID.fromString("4eef06bd-c5d2-4a75-9d30-3ac302c59035"))).thenReturn(true);

        boolean result = packageService.deletePackage(UUID.fromString("4eef06bd-c5d2-4a75-9d30-3ac302c59035"));

        assertTrue(result);
    }

    @Test
    void getPackageShouldReturnPackageWhenPackageFound() {
        when(packageRepository.findById(UUID.fromString("4eef06bd-c5d2-4a75-9d30-3ac302c59035"))).thenReturn(Optional.of(TestConstants.package1));

        PackageResponse result = packageService.getPackage(UUID.fromString("4eef06bd-c5d2-4a75-9d30-3ac302c59035"), CurrencyCode.USD);

        assertNotNull(result);
        assertNotNull(result.id());
        assertThat(result.currencyCode(), is("USD"));
        assertThat(result.name(), is("oldname"));
        assertThat(result.description(), is("olddesc"));
        assertThat(result.totalPrice(), is(BigDecimal.valueOf(10)));
        assertThat(result.products().size(), is(1));
        assertThat(result.products().get(0).id(), is("a1"));
        assertThat(result.products().get(0).name(), is("helmet"));
        assertThat(result.products().get(0).currencyCode(), is("USD"));
        assertThat(result.products().get(0).price(), is(BigDecimal.TEN));
    }

    @Test
    void getPackageShouldReturnPackagesWhenPackageFound() {
        when(packageRepository.findAll()).thenReturn(List.of(TestConstants.package1));

        List<PackageResponse> result = packageService.getPackage(CurrencyCode.USD);

        assertNotNull(result);
        assertNotNull(result.get(0).id());
        assertThat(result.get(0).currencyCode(), is("USD"));
        assertThat(result.get(0).name(), is("oldname"));
        assertThat(result.get(0).description(), is("olddesc"));
        assertThat(result.get(0).totalPrice(), is(BigDecimal.valueOf(10)));
        assertThat(result.get(0).products().size(), is(1));
        assertThat(result.get(0).products().get(0).id(), is("a1"));
        assertThat(result.get(0).products().get(0).name(), is("helmet"));
        assertThat(result.get(0).products().get(0).currencyCode(), is("USD"));
        assertThat(result.get(0).products().get(0).price(), is(BigDecimal.TEN));
    }

}