package ru.vsu.amm.java;

import java.util.Objects;

public class StringInstrument extends Instrument implements Stringable {

    private int numberStrings;

    public StringInstrument(String name, double weight, Material material, int numberStrings){
        super(name, weight, material);
        this.numberStrings = numberStrings;
    }

    public StringInstrument() {}

    public int getNumberStrings () {
        return numberStrings;
    }
    @Override
    public void play() {
        System.out.println(getName() + ": (мелодия на струнах)");
        brokenString();
    }

    @Override
    public void brokenString() {
        System.out.println("Струна порвалась! Нужно заменить");
    }

    @Override
    public String toString() {
        return "Струнный инструмент" +
                super.toString() +
                "Количество струн: " + getNumberStrings();
    }

    @Override
    public boolean equals(Object obj) {
        StringInstrument that = (StringInstrument) obj;
        return numberStrings == that.numberStrings && super.equals(obj);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getWeight(), getMaterial(), numberStrings);
    }
}
