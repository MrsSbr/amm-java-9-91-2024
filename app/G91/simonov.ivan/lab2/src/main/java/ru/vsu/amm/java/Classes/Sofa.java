package ru.vsu.amm.java.Classes;

import ru.vsu.amm.java.Enums.Material;

import java.util.Objects;

public class Sofa extends Furniture {

    private final int places;

    public Sofa(String name,
                Material material,
                double price,
                boolean isShowcaseSample,
                int places) {
        super(name, material, price, isShowcaseSample);
        this.places = places;
    }

    @Override
    public void move() {
        System.out.println("Диван " + name + " перемещается с тяжелым глухим звуком!");
    }

    @Override
    public String toString() {
        return "Диван\n" +
                super.toString() +
                "\nКол-во мест: " + places;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (super.equals(obj)) {
            Sofa other = (Sofa) obj;
            return places == other.places;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), places);
    }
}
