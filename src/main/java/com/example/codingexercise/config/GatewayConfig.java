package com.example.codingexercise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class GatewayConfig {

    @Value("${rest.product.timeout.connect}")
    private long connectTimeout;

    @Value("${rest.product.timeout.read}")
    private long readTimeout;

    @Value("${rest.product.username}")
    private String username;

    @Value("${rest.product.password}")
    private String password;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.
                setConnectTimeout(Duration.ofMillis(connectTimeout)).
                setReadTimeout(Duration.ofMillis(readTimeout)).
                build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(username, password));
        return restTemplate;
    }
}
