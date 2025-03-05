package ru.vsu.amm.java;

import java.util.Objects;

public class WindInstrument extends Instrument {
    private int tubeLength;

    public WindInstrument(String model, double weight, Material material, int tubeLength) {
        super(model, weight, material);
        this.tubeLength = tubeLength;
    }

    public int getTubeLength() {
        return tubeLength;
    }

    @Override
    public void playSound() {
        System.out.println(getModel() + " is playing a tune with a flow of air.");
    }

    @Override
    public String toString() {
        return "Wind Instrument:\n" + super.toString() +
                "\nTube Length: " + tubeLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WindInstrument)) return false;
        if (!super.equals(o)) return false;
        WindInstrument that = (WindInstrument) o;
        return tubeLength == that.tubeLength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tubeLength);
    }

    @Override
    public void tune() {
        System.out.println(getModel() + " (Wind Instrument) is tuning by adjusting its air column.");
    }
}
