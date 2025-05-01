package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.model.dto.CarDto;

import java.math.BigDecimal;

public class CarPriceUtil {
    //todo может быть в дальнейшем накручу какие-то скейлеры цены от других зависимостей
    public static BigDecimal getCarPrice(CarDto car) {
        switch (car.getCarClass()) {
            case ECONOMY -> {
                return BigDecimal.valueOf(10);
            }
            case COMFORT -> {
                return BigDecimal.valueOf(20);
            }

            case COMFORT_PLUS -> {
                return BigDecimal.valueOf(30);
            }
            case BUSINESS -> {
                return BigDecimal.valueOf(40);
            }
            default -> {
                return BigDecimal.ZERO;
            }
        }
    }
}
