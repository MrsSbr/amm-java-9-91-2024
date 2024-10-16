package ru.vsu.amm.java;

import java.util.Objects;

public abstract class InstrumentImpl implements MusicalInstrument {

    private String name;
    private double weight;
    private String material;

    public InstrumentImpl(String name, double weight, String material) {
        this.name = name;
        this.weight = weight;
        this.material = material;
    }

    public InstrumentImpl() {}

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String toString(){
        String result = "Название инструмента: " + name +
                "\nВес: " + weight +
                "\nМатериал: " + material;
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

        InstrumentImpl that = (InstrumentImpl) obj;
        return name.equals(that.name) && weight == that.weight && material.equals(that.material);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, weight, material);
    }
}