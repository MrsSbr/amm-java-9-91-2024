package ru.vsu.amm.java.Classes;

import java.util.Objects;

public class Sofa extends FurnitureImpl {

    private int places;

    public Sofa(String name,
                 String material,
                 double price,
                 boolean isShowcaseSample,
                 int places) {
        super(name, material, price, isShowcaseSample);
        this.places = places;
    }

    public Sofa() {}

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
        Sofa other = (Sofa) obj;
        return places == other.places && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), places);
    }
}
