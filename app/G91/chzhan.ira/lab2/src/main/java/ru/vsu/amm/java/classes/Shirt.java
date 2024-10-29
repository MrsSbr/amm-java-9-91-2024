package ru.vsu.amm.java.classes;
import ru.vsu.amm.java.abstracts.Clothes;

import java.util.Objects;
import ru.vsu.amm.java.enums.Size;
public class Shirt extends Clothes {

    private Size size;

    public Shirt(String name, String description, double price, Size size) {
        super(name, description, price);
        this.size = size;
    }

    public Size getSize() {
        return size;
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
}
