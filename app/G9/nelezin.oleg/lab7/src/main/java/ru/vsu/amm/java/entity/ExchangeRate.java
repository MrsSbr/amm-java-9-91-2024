package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExchangeRate {

    private Long id;

    private Long baseCurrencyId;

    private Long targetCurrencyId;

    private BigDecimal rate;
}
