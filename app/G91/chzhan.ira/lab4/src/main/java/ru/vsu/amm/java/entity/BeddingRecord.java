package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Colors;
import ru.vsu.amm.java.enums.Size;
import ru.vsu.amm.java.enums.Material;

import java.time.LocalDate;
import java.util.Objects;

public class BeddingRecord {
    public LocalDate date;
    public String name;
    public Size size;
    public Colors color;
    public Material material;

    public BeddingRecord(LocalDate date, String name, Size size, Colors color, Material material) {
        this.date = date;
        this.name = name;
        this.size = size;
        this.color = color;
        this.material = material;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Size getSize() {
        return size;
    }

    public Material getMaterial() {
        return material;
    }

    public Colors getColor() {
        return color;
    }

    @Override
    public String toString() {
        return date + ";" + name + ";" + size + ";" + material + ";" + color;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BeddingRecord)) return false;
        BeddingRecord other = (BeddingRecord) obj;
        return getDate().equals(other.getDate()) &&
                getName().equals(other.getName()) &&
                getSize().equals(other.getSize()) &&
                getMaterial().equals(other.getMaterial()) &&
                getColor().equals(other.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getName(), getSize(), getMaterial(), getColor());
    }
}
