package ru.vsu.amm.java;
import java.util.Objects;
public class Shirt extends Wearable {

    private String size;

    public Shirt(String name, String description, double price, String size) {
        super(name, description, price);
        this.size = size;
    }

    @Override
    public String toString() {
        return "Shirt: " + getName() + ", Description: " + getDescription() + ", Price: " + getPrice() + ", Size: " + size;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Shirt)) return false;
        Shirt other = (Shirt) obj;
        return getName().equals(other.getName()) &&
                getDescription().equals(other.getDescription()) &&
                getPrice() == other.getPrice() &&
                getSize().equals(other.getSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getPrice(), getSize());
    }

    public String getSize() {
        return size;
    }
}
