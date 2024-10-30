package ru.vsu.amm.java.records;

import ru.vsu.amm.java.enums.DrinkName;

import java.time.LocalDate;
import java.time.LocalTime;

import static ru.vsu.amm.java.service.Logg.logger;

public class DrinkRecord {
    private DrinkName drinkName;
    private LocalDate date;
    private LocalTime time;

    public DrinkRecord(DrinkName drinkName, LocalDate date, LocalTime time) {
        this.drinkName = drinkName;
        this.date = date;
        this.time = time;
        ListDrinks.listDrinks.add(drinkName.name());
        ListDrinks.listDrinks.add("Something");

        logger.fine("created drink: " + drinkName);
    }

    public void setDrinkName(DrinkName drinkName) {
        this.drinkName = drinkName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDrinkName() {
        return drinkName.name();
    }
}


