package ru.vsu.amm.java;

import java.util.Objects;

public class WindInstrument extends Instrument implements Playable {

    private int lengthWindChannel;

    public WindInstrument(String name, double weight, Material material, int lengthWindChannel) {
        super(name, weight, material);
        this.lengthWindChannel = lengthWindChannel;
    }

    public WindInstrument() {}

    public int getLengthWindChannel () {
        return lengthWindChannel;
    }
    @Override
    public void play() {

        System.out.println(getName() + ": (мелодия на духовом инструменте)");
    }

    @Override
    public String toString() {
        return "Духовой инструмент" +
                super.toString() +
                "Длина канала: " + getLengthWindChannel();
    }

    @Override
    public boolean equals(Object obj) {
        WindInstrument that = (WindInstrument) obj;
        return lengthWindChannel == that.lengthWindChannel && super.equals(obj);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getWeight(), getMaterial(), getLengthWindChannel());
    }
}
