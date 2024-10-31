package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.abstracts.Clothes;

import java.util.Objects;

import ru.vsu.amm.java.enums.Size;

public class Pants extends Clothes {

    private Size waistSize;

    public Pants(String name, String description, double price, Size waistSize) {
        super(name, description, price);
        this.waistSize = waistSize;
    }

    public Size getWaistSize() {
        return waistSize;
    }

    @Override
    public String toString() {
        return "Pants: " + getName() + ", Description: " + getDescription() + ", Price: " + getPrice() + ", Waist Size: " + waistSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pants)) return false;
        Pants other = (Pants) obj;
        return getName().equals(other.getName()) &&
                getDescription().equals(other.getDescription()) &&
                getPrice() == other.getPrice() &&
                getWaistSize().equals(other.getWaistSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getPrice(), getWaistSize());
    }
}
