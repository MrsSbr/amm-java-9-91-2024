package ru.vsu.amm.java;

import java.util.Objects;

public class Part {
    private final DetailTypes type;

    public Part(DetailTypes type) {
        this.type = type;
    }

    public DetailTypes getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return type == part.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
