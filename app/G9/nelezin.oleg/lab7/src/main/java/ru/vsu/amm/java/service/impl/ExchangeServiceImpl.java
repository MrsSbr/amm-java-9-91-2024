package ru.vsu.amm.java.service.impl;

import ru.vsu.amm.java.entity.Currency;
import ru.vsu.amm.java.entity.ExchangeRate;
import ru.vsu.amm.java.exception.DataNotFoundException;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.repository.ExchangeRateRepository;
import ru.vsu.amm.java.service.CurrencyService;
import ru.vsu.amm.java.service.ExchangeService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ExchangeServiceImpl implements ExchangeService {

    private static final Logger log = Logger.getLogger(ExchangeServiceImpl.class.getName());

    private final CurrencyService currencyService;

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeServiceImpl() {
        log.info("call ExchangeServiceImpl constructor");
        currencyService = new CurrencyServiceImpl();
        exchangeRateRepository = new ExchangeRateRepository();
    }

    @Override
    public BigDecimal exchangeCurrencies(String firstCurrencyCode,
                                         String secondCurrencyCode,
                                         double amount) {
        log.info("call exchangeCurrencies");
        Currency firstCurrency = currencyService.findByCode(firstCurrencyCode);
        Currency secondCurrency = currencyService.findByCode(secondCurrencyCode);

        try {
            ExchangeRate exchangeRate = exchangeRateRepository
                    .findByCodes(firstCurrency.getId(), secondCurrency.getId()).orElseThrow(
                            () -> new DataNotFoundException("Нет данных о курсе")
                    );
            return exchangeRate.getRate().multiply(BigDecimal.valueOf(amount));

        } catch (SQLException e) {
            log.severe("error DatabaseException");
            throw new DatabaseException(e.getMessage());
        }

    }
}
