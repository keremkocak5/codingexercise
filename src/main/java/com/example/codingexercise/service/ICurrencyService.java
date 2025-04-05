package com.example.codingexercise.service;

import com.example.codingexercise.enums.CurrencyCode;

import java.math.BigDecimal;

public interface ICurrencyService {

    BigDecimal getCurrencyBaseUsd(CurrencyCode currencyCode);

}
