package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {

    private Long id;

    private Long baseCurrencyId;

    private Long targetCurrencyId;

    private BigDecimal rate;
}
