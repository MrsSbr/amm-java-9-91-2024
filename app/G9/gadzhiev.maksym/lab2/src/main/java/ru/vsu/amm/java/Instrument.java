package ru.vsu.amm.java;

import java.util.Objects;

public abstract class Instrument implements Playable{

    private String name;
    private double weight;
    private Material material;

    public Instrument(String name, double weight, Material material) {
        this.name = name;
        this.weight = weight;
        this.material = material;
    }

    public Instrument() {}

    public String getName() {

        return name;
    }

    public double getWeight() {

        return weight;
    }

    public Material getMaterial() {

        return material;
    }

    public void tune() {
        System.out.println(name + " настраивается");
    }
    @Override
    public String toString(){
        String result = "\nНазвание инструмента: " + name +
                "\nВес: " + weight +
                "\nМатериал: " + material + "\n";
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }

        if (obj == null || getClass() != obj.getClass()){
            return false;
        }

        Instrument that = (Instrument) obj;
        return name.equals(that.name) && weight == that.weight && material.equals(that.material);
    }

    @Override
    public int hashCode(){

        return Objects.hash(name, weight, material);
    }
}