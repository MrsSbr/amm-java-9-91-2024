package ru.vsu.amm.java;

import java.util.Objects;

public class StringInstrument extends InstrumentImpl {

    private int numberStrings;

    public StringInstrument(String name, double weight, String material, int numberStrings){
        super(name, weight, material);
        this.numberStrings = numberStrings;
    }

    public StringInstrument() {}

    @Override
    public void play() {
        System.out.println(getName() + ": (мелодия на струнах)");
    }

    @Override
    public String toString() {
        return "Струнный инструмент" +
                super.toString() +
                "Количество струн: \n" + numberStrings;
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
