package ru.vsu.amm.java.records;

import ru.vsu.amm.java.enums.CoffeeType;

import java.time.LocalDateTime;

public record CoffeeRecord(CoffeeType name, LocalDateTime date, long preparationTime, double cost) {
    public double getPriceToTimeRatio() {
        if (preparationTime == 0) {
            throw new IllegalArgumentException("Preparation time cannot be zero.");
        }

        return cost / preparationTime;
    }
}
