package ru.vsu.amm.java.entity;

import java.time.LocalDateTime;
import ru.vsu.amm.java.enums.DrinkName;

public class DrinkRecord {
    private DrinkName drinkName;
    private LocalDateTime timestamp;

    public DrinkRecord(DrinkName drinkName, LocalDateTime timestamp) {
        this.drinkName = drinkName;
        this.timestamp = timestamp;
    }

    public DrinkName getDrinkName() {
        return drinkName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "DrinkRecord{" +
                "drinkName=" + drinkName +
                ", timestamp=" + timestamp +
                '}';
    }
}