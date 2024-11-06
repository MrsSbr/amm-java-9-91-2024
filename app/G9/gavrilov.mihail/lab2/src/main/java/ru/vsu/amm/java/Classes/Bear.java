package ru.vsu.amm.java.Classes;

import ru.vsu.amm.java.Enum.Nutrition;

import java.util.Objects;

public class Bear extends Animal {
    private String colorWool;

    public Bear(String name, double weight, Nutrition methodOfNutrition,
                int paws, String colorWool){
        super(name, weight, methodOfNutrition, paws);
        this.colorWool = colorWool;
    }

    @Override
    public void animalSay() {
        super.animalSay();
        System.out.println( "Ревёт!\n");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Bear bear)) {
            return false;
        }
        return super.equals(obj) && colorWool.equals(bear.colorWool);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), colorWool);
    }

    @Override
    public String toString() {
        return super.toString() + "Цвет шерсти: " + colorWool + '\n';
    }
}
