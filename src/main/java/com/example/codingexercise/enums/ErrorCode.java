package com.example.codingexercise.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    PRODUCT_NOT_FOUND("I0001", "Product not found", HttpStatus.NOT_FOUND),
    PACKAGE_NOT_FOUND("I0002", "Package not found", HttpStatus.NOT_FOUND),
    RATE_CONNECTION_ERROR("I0003", "Could not retrieve rates", HttpStatus.INTERNAL_SERVER_ERROR),
    PACKAGE_CONNECTION_ERROR("I0004", "Could not retrieve packages", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER_ERROR("I0000", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    ErrorCode(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

}
