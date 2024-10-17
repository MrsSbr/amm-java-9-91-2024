package ru.vsu.amm.java;
import java.util.Objects;
public class Pants extends Wearable {

    private String waistSize;

    public Pants(String name, String description, double price, String waistSize) {
        super(name, description, price);
        this.waistSize = waistSize;
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

    public String getWaistSize() {
        return waistSize;
    }
}
