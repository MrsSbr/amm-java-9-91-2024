package ru.vsu.amm.java.utils;

import java.math.BigDecimal;

public class SessionPriceUtil {
    public static final BigDecimal RUB_BY_MINUTE = BigDecimal.valueOf(40);

    public static BigDecimal getSessionPrice(Short minutes) {
        return RUB_BY_MINUTE.multiply(BigDecimal.valueOf(minutes));
    }
}
