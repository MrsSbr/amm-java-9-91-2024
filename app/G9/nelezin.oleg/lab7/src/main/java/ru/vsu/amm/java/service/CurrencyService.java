package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Currency;

import java.util.List;

public interface CurrencyService {

    List<Currency> getAllCurrencies();

    Currency findByCode(String code);
}
