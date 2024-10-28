package ru.vsu.amm.java;
import java.util.Objects;

public class Fighter extends MilitaryAircraft {
    private final int firePower;

    public Fighter(String model, int maxSpeed, int armament) {
        super(model, maxSpeed);
        this.firePower = armament;
    }

    @Override
    public String toString() {
        return "FighterJet{" +
                "model='" + getModel() + '\'' +
                ", maxSpeed=" + getMaxSpeed() +
                ", firepower=" + firePower +
                '}';
    }

    @Override
    public String mission() {
        return getModel() + " attacks!";
    }

    @Override
    public boolean equals(Object obj) {
        Fighter that = (Fighter) obj;
        if (obj == null)
            return false;
        else
            return (firePower == that.firePower && super.equals(obj));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firePower);
    }

}
