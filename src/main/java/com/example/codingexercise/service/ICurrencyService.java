package com.example.codingexercise.service;

import com.example.codingexercise.enums.CurrencyCodeEnum;

import java.math.BigDecimal;

public interface ICurrencyService {

    BigDecimal getCurrencyBaseUsd(CurrencyCodeEnum currencyCode);

}
