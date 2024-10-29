package ru.vsu.amm.java.classes;
import ru.vsu.amm.java.abstracts.Clothes;

import java.util.Objects;
public class Dress extends Clothes {

    public enum DressLength {
        MINI,
        MIDDLE,
        LONG
    }

    private DressLength length;

    public Dress(String name, String description, double price, DressLength length) {
        super(name, description, price);
        this.length = length;
    }

    public DressLength getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Dress: " + getName() + ", Description: " + getDescription() + ", Price: " + getPrice() + ", Length: " + length;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Dress)) return false;
        Dress other = (Dress) obj;
        return getName().equals(other.getName()) &&
                getDescription().equals(other.getDescription()) &&
                getPrice() == other.getPrice() &&
                getLength().equals(other.getLength());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getPrice(), getLength());
    }
}