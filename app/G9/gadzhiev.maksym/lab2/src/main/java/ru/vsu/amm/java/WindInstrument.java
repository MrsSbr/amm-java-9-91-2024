package ru.vsu.amm.java;

import java.util.Objects;

public class WindInstrument extends InstrumentImpl implements Playable {

    private int lengthWindСhannel;

    public WindInstrument(String name, double weight, String material, int lengthWindСhannel) {
        super(name, weight, material);
        this.lengthWindСhannel = lengthWindСhannel;
    }

    public WindInstrument() {}

    @Override
    public void play() {

        System.out.println(getName() + ": (мелодия на духовом инструменте)");
    }

    @Override
    public String toString() {
        return "Духовой инструмент" +
                super.toString() +
                "Длина канала: " + lengthWindСhannel;
    }

    @Override
    public boolean equals(Object obj) {
        WindInstrument that = (WindInstrument) obj;
        return lengthWindСhannel == that.lengthWindСhannel && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getWeight(), getMaterial(), lengthWindСhannel);
    }
}
