package com.example.codingexercise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class FrankfurterGatewayConfig {

    @Value("${rest.frankfurter.timeout.connect}")
    private long connectTimeout;

    @Value("${rest.frankfurter.timeout.read}")
    private long readTimeout;

    @Bean
    public RestTemplate frankfurterRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.
                setConnectTimeout(Duration.ofMillis(connectTimeout)).
                setReadTimeout(Duration.ofMillis(readTimeout)).
                build();
    }
}
