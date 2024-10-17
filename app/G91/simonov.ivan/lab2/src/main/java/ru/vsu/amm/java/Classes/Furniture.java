package ru.vsu.amm.java.Classes;

import ru.vsu.amm.java.Enums.Material;
import ru.vsu.amm.java.Interfaces.Salable;

import java.util.Objects;

public abstract class Furniture implements Salable {

    protected String name;

    protected Material material;

    protected double price;

    protected boolean isShowcaseSample;

    public Furniture(String name,
                     Material material,
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

        Furniture other = (Furniture) obj;
        return name.equals(other.name) && material.equals(other.material) && price == other.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, material, price);
    }
}
