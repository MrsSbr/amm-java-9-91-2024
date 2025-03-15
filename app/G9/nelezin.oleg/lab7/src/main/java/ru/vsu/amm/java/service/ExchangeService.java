package ru.vsu.amm.java.service;

import java.math.BigDecimal;

public interface ExchangeService {

    BigDecimal exchangeCurrencies(String firstCurrencyCode, String secondCurrencyCode, double amount);
}
