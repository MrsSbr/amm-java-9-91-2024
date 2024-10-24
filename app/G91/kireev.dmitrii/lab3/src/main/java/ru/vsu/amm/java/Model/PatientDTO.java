package ru.vsu.amm.java.Model;

import java.time.LocalDate;
import java.util.Objects;

public record PatientDTO(String name, String surname, String patronymic, Boolean isHealthy, String result, LocalDate date) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientDTO that = (PatientDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(patronymic, that.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, patronymic);
    }
}
