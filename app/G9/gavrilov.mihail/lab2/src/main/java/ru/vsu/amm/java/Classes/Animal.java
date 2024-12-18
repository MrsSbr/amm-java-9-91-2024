package ru.vsu.amm.java.Classes;

import ru.vsu.amm.java.Enum.Nutrition;

import java.util.Objects;

public abstract class Animal {
    private String name;

    private double weight;

    private Nutrition methodOfNutrition;

    private int paws;

    Animal(){}

    Animal(String name, double weight, Nutrition methodOfNutrition,
         int paws){
        this.name = name;
        this.weight = weight;
        this.methodOfNutrition = methodOfNutrition;
        this.paws = paws;
    }

    public String getName(){
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getPaws() {
        return paws;
    }

    public Nutrition getMethodOfNutrition() {
        return methodOfNutrition;
    }

    void animalSay(){
        System.out.print("Животное ");
    }

    @Override
    public String toString(){
        return "имя животного: " + name + "\nВес: " + weight
                + "\nТип питания: " + methodOfNutrition +
                "\nКоличество лап: " + paws +'\n';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, methodOfNutrition, paws);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal an =  (Animal) o;

        return an.name.equals(name) && an.weight == weight
                && an.methodOfNutrition.equals(methodOfNutrition)
                && an.paws == paws;
    }


}
