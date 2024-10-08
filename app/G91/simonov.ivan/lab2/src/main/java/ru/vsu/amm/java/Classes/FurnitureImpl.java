package ru.vsu.amm.java.Classes;

import ru.vsu.amm.java.Interfaces.Furniture;
import java.util.Objects;

public abstract class FurnitureImpl implements Furniture {

    protected String name;

    protected String material;

    protected double price;

    protected boolean isShowcaseSample;

    public FurnitureImpl(String name,
                         String material,
                         double price,
                         boolean isShowcaseSample) {
        this.name = name;
        this.material = material;
        this.price = price;
        this.isShowcaseSample = isShowcaseSample;
    }

    @Override
    public void placeOnShowcase() {
        if (isShowcaseSample) {
            System.out.println("Мебель " + name + " уже находится на витрине!");
        } else {
            isShowcaseSample = true;
            System.out.println("Мебель " + name + " успешно выставлена на витрину!");
        }
    }

    @Override
    public void removeFromShowcase() {
        if (!isShowcaseSample) {
            System.out.println("Мебель " + name + " не находится на витрине!");
        } else {
            isShowcaseSample = false;
            System.out.println("Мебель " + name + " успешно убрана с витрины!");
        }
    }

    @Override
    public String toString() {
        return "Название: " + name +
                "\nМатериал: " + material +
                "\nЦена: " + price +
                "\nВитринный образец: " + (isShowcaseSample ? "Да" : "Нет");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        FurnitureImpl other = (FurnitureImpl) obj;
        return name.equals(other.name) && material.equals(other.material) && price == other.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, material, price);
    }
}
