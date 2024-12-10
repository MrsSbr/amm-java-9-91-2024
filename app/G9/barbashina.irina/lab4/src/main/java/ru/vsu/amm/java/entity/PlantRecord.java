package ru.vsu.amm.java.entity;

import java.time.LocalDate;
import java.util.Objects;

import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import ru.vsu.amm.java.enums.FertilizerBrand;
import ru.vsu.amm.java.enums.Plant;

@Builder
@Getter
@Setter
@NoArgsConstructor

public class PlantRecord {
    private static final Logger logger = Logger.getLogger(PlantRecord.class.getName());
    private LocalDate date;
    private Plant plantName;
    private int waterAmount;
    private FertilizerBrand fertilizerBrand;

    public PlantRecord(LocalDate date, Plant plantName, int waterAmount, FertilizerBrand fertilizerBrand) {
        this.date = date;
        this.plantName = plantName;
        this.waterAmount = waterAmount;
        this.fertilizerBrand = fertilizerBrand;
        logger.log(Level.INFO, "Created PlantRecord for plant {0}", plantName);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass()!=o.getClass()) return false;
        PlantRecord that = (PlantRecord) o;
        return waterAmount == that.waterAmount &&
                Objects.equals(date, that.date) &&
                Objects.equals(plantName, that.plantName) &&
                Objects.equals(fertilizerBrand, that.fertilizerBrand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, plantName, waterAmount, fertilizerBrand);
    }

    @Override
    public String toString() {
        return "date=" + date +
                ", plantName='" + plantName + '\'' +
                ", waterAmount=" + waterAmount +
                ", fertilizerBrand='" + fertilizerBrand + '\'';
    }

}
