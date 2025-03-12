package ru.vsu.amm.java.service.impl;

import ru.vsu.amm.java.entity.Currency;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.repository.CurrencyRepository;
import ru.vsu.amm.java.service.CurrencyService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl() {
        currencyRepository = new CurrencyRepository();
    }

    @Override
    public List<Currency> getAllCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        try {
            currencies = currencyRepository.findAll();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return currencies;
    }
}
