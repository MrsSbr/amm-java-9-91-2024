package ru.vsu.amm.java.Model;

import ru.vsu.amm.java.Enum.Illness;

import java.time.LocalDate;
import java.util.Objects;

public record Patient(String individualNumber, String name, String surname, String patronymic, Boolean isHealthy, Illness illness, LocalDate date) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient that = (Patient) o;
        return Objects.equals(individualNumber, that.individualNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(individualNumber);
    }
}
