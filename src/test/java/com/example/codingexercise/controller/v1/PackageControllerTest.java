package com.example.codingexercise.controller.v1;

import com.example.codingexercise.config.GlobalExceptionHandler;
import com.example.codingexercise.constants.TestConstants;
import com.example.codingexercise.enums.CurrencyCode;
import com.example.codingexercise.gateway.dto.incoming.PackageRequest;
import com.example.codingexercise.gateway.dto.outgoing.PackageResponse;
import com.example.codingexercise.service.impl.PackageService;
import com.example.codingexercise.service.impl.PackageServiceBaseUsdCurrencyConverterDecorator;
import com.example.codingexercise.service.impl.PackageServiceFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class PackageControllerTest {

    @InjectMocks
    private PackageController packageController;
    @Mock
    private PackageService packageService;
    @Mock
    private PackageServiceBaseUsdCurrencyConverterDecorator packageServiceBaseUsdCurrencyConverterDecorator;
    @Mock
    private PackageServiceFactory packageServiceFactory;

    private MockMvc mockMvc;
    private JacksonTester<PackageRequest> packageRequestJacksonTester;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(packageController).setControllerAdvice(GlobalExceptionHandler.class).build();
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    void createShouldReturnPackageWhenCurrencyNotDeclared() throws Exception {
        when(packageServiceFactory.getPackageService(Optional.empty())).thenReturn(packageService);
        when(packageService.createPackage(TestConstants.packageRequest, CurrencyCode.USD)).thenReturn(TestConstants.packageResponse);

        MvcResult result = mockMvc.perform(post("/v1/packages/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(packageRequestJacksonTester.write(TestConstants.packageRequest).getJson()))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        PackageResponse packageResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<PackageResponse>() {
        });
        assertThat(packageResponse, is(TestConstants.packageResponse));
        verify(packageService, times(1)).createPackage(TestConstants.packageRequest, CurrencyCode.USD);
        verify(packageServiceBaseUsdCurrencyConverterDecorator, times(0)).createPackage(any(), any());
    }

    @Test
    void createShouldReturnPackageWhenCurrencyDeclared() throws Exception {
        when(packageServiceFactory.getPackageService(Optional.of(CurrencyCode.BRL))).thenReturn(packageServiceBaseUsdCurrencyConverterDecorator);
        when(packageServiceBaseUsdCurrencyConverterDecorator.createPackage(TestConstants.packageRequest, CurrencyCode.BRL)).thenReturn(TestConstants.packageResponse);

        MvcResult result = mockMvc.perform(post("/v1/packages/currency/BRL")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(packageRequestJacksonTester.write(TestConstants.packageRequest).getJson()))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        PackageResponse packageResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<PackageResponse>() {
        });
        assertThat(packageResponse, is(TestConstants.packageResponse));
        verify(packageService, times(0)).createPackage(any(), any());
        verify(packageServiceBaseUsdCurrencyConverterDecorator, times(1)).createPackage(TestConstants.packageRequest, CurrencyCode.BRL);
    }

    @Test
    void updateShouldReturnPackageWhenCurrencyNotDeclared() throws Exception {
        when(packageServiceFactory.getPackageService(Optional.empty())).thenReturn(packageService);
        when(packageService.updatePackage("4eef06bd-c5d2-4a75-9d30-3ac302c59035", TestConstants.packageRequest, CurrencyCode.USD)).thenReturn(TestConstants.packageResponse);

        MvcResult result = mockMvc.perform(post("/v1/packages/id/4eef06bd-c5d2-4a75-9d30-3ac302c59035")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(packageRequestJacksonTester.write(TestConstants.packageRequest).getJson()))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        PackageResponse packageResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<PackageResponse>() {
        });
        assertThat(packageResponse, is(TestConstants.packageResponse));
        verify(packageService, times(1)).updatePackage("4eef06bd-c5d2-4a75-9d30-3ac302c59035", TestConstants.packageRequest, CurrencyCode.USD);
        verify(packageServiceBaseUsdCurrencyConverterDecorator, times(0)).updatePackage(any(), any(), any());
    }

    @Test
    void updateShouldReturnPackageWhenCurrencyDeclared() throws Exception {
        when(packageServiceFactory.getPackageService(Optional.of(CurrencyCode.TRY))).thenReturn(packageServiceBaseUsdCurrencyConverterDecorator);
        when(packageServiceBaseUsdCurrencyConverterDecorator.updatePackage("4eef06bd-c5d2-4a75-9d30-3ac302c59035", TestConstants.packageRequest, CurrencyCode.TRY)).thenReturn(TestConstants.packageResponse);

        MvcResult result = mockMvc.perform(post("/v1/packages/id/4eef06bd-c5d2-4a75-9d30-3ac302c59035/currency/TRY")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(packageRequestJacksonTester.write(TestConstants.packageRequest).getJson()))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        PackageResponse packageResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<PackageResponse>() {
        });
        assertThat(packageResponse, is(TestConstants.packageResponse));
        verify(packageService, times(0)).updatePackage(any(), any(), any());
        verify(packageServiceBaseUsdCurrencyConverterDecorator, times(1)).updatePackage("4eef06bd-c5d2-4a75-9d30-3ac302c59035", TestConstants.packageRequest, CurrencyCode.TRY);
    }

    @Test
    void getShouldReturnPackageWhenIdDeclaredCurrencyNotDeclared() throws Exception {
        when(packageServiceFactory.getPackageService(Optional.empty())).thenReturn(packageService);
        when(packageService.getPackage("4eef06bd-c5d2-4a75-9d30-3ac302c59035", CurrencyCode.USD)).thenReturn(TestConstants.packageResponse);

        MvcResult result = mockMvc.perform(get("/v1/packages/id/4eef06bd-c5d2-4a75-9d30-3ac302c59035"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        PackageResponse packageResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<PackageResponse>() {
        });
        assertThat(packageResponse, is(TestConstants.packageResponse));
        verify(packageService, times(1)).getPackage("4eef06bd-c5d2-4a75-9d30-3ac302c59035", CurrencyCode.USD);
        verify(packageServiceBaseUsdCurrencyConverterDecorator, times(0)).getPackage(any(), any());
    }

    @Test
    void getShouldReturnPackageWhenIdDeclaredCurrencyDeclared() throws Exception {
        when(packageServiceFactory.getPackageService(Optional.of(CurrencyCode.AUD))).thenReturn(packageServiceBaseUsdCurrencyConverterDecorator);
        when(packageServiceBaseUsdCurrencyConverterDecorator.getPackage("4eef06bd-c5d2-4a75-9d30-3ac302c59035", CurrencyCode.AUD)).thenReturn(TestConstants.packageResponse);

        MvcResult result = mockMvc.perform(get("/v1/packages/id/4eef06bd-c5d2-4a75-9d30-3ac302c59035/currency/AUD"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        PackageResponse packageResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<PackageResponse>() {
        });
        assertThat(packageResponse, is(TestConstants.packageResponse));
        verify(packageService, times(0)).getPackage(any(), any());
        verify(packageServiceBaseUsdCurrencyConverterDecorator, times(1)).getPackage("4eef06bd-c5d2-4a75-9d30-3ac302c59035", CurrencyCode.AUD);
    }

    @Test
    void getShouldReturnAllPackagesWhenCurrencyNotDeclared() throws Exception {
        when(packageServiceFactory.getPackageService(Optional.empty())).thenReturn(packageService);
        when(packageService.getPackage(CurrencyCode.USD)).thenReturn(List.of(TestConstants.packageResponse));

        MvcResult result = mockMvc.perform(get("/v1/packages/"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        List<PackageResponse> packageResponses = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PackageResponse>>() {
        });
        assertThat(packageResponses.get(0), is(TestConstants.packageResponse));
        verify(packageService, times(1)).getPackage(CurrencyCode.USD);
        verify(packageServiceBaseUsdCurrencyConverterDecorator, times(0)).getPackage(any());
    }

    @Test
    void getShouldReturnAllPackagesWhenCurrencyDeclared() throws Exception {
        when(packageServiceFactory.getPackageService(Optional.of(CurrencyCode.BGN))).thenReturn(packageServiceBaseUsdCurrencyConverterDecorator);
        when(packageServiceBaseUsdCurrencyConverterDecorator.getPackage(CurrencyCode.BGN)).thenReturn(List.of(TestConstants.packageResponse));

        MvcResult result = mockMvc.perform(get("/v1/packages/currency/BGN"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        List<PackageResponse> packageResponses = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PackageResponse>>() {
        });
        assertThat(packageResponses.get(0), is(TestConstants.packageResponse));
        verify(packageService, times(0)).getPackage(any());
        verify(packageServiceBaseUsdCurrencyConverterDecorator, times(1)).getPackage(CurrencyCode.BGN);
    }

    @Test
    void getShouldReturnNoDataFoundPackagesWhenInvalidCurrencyDeclared() throws Exception {
        mockMvc.perform(get("/v1/packages/currency/XXX"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void deleteShouldCallServiceOnceWhenIdNotEmpty() throws Exception {
        when(packageService.deletePackage("4eef06bd-c5d2-4a75-9d30-3ac302c59035")).thenReturn(true);

        mockMvc.perform(delete("/v1/packages/id/4eef06bd-c5d2-4a75-9d30-3ac302c59035"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")))
                .andReturn();

        verify(packageService, times(1)).deletePackage("4eef06bd-c5d2-4a75-9d30-3ac302c59035");
    }

    @Test
    void deleteShouldReturnNoDataFoundWhenIdEmpty() throws Exception {
        mockMvc.perform(delete("/v1/packages/id/"))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(packageService, times(0)).deletePackage(any());
    }

}