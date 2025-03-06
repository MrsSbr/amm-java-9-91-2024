package ru.vsu.amm.java;

import java.util.Objects;

public class StringInstrument extends Instrument {
    private int numberOfStrings;

    public StringInstrument(String model, double weight, Material material, int numberOfStrings) {
        super(model, weight, material);
        this.numberOfStrings = numberOfStrings;
    }

    public int getNumberOfStrings() {
        return numberOfStrings;
    }

    @Override
    public void playSound() {
        System.out.println(getModel() + " is playing a melody using its strings.");
        checkStrings();
    }

    public void checkStrings() {
        System.out.println("Checking: " + numberOfStrings + " strings are in proper condition.");
    }

    @Override
    public String toString() {
        return "String Instrument:\n" + super.toString() +
                "\nNumber of Strings: " + numberOfStrings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringInstrument)) return false;
        if (!super.equals(o)) return false;
        StringInstrument that = (StringInstrument) o;
        return numberOfStrings == that.numberOfStrings;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numberOfStrings);
    }

    @Override
    public void tune() {
        System.out.println(getModel() + " (String Instrument) is tuning by adjusting its strings.");
    }
}
