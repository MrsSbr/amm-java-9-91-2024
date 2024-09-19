package ru.vsu.amm.java;

import java.util.Objects;

public class MilitarySatellite extends SatelliteImpl {

    private String weapon;

    public MilitarySatellite(String name,
                             double width,
                             double length,
                             boolean isWorking,
                             String weapon) {
        super(name, width, length, isWorking);
        this.weapon = weapon;
    }

    public MilitarySatellite() {}

    @Override
    public void perform() {
        System.out.println(getName() + " начинает наблюдать за врагом, бип-бип");
    }

    @Override
    public String toString() {
        return "Военный спутник\n" +
                super.toString() +
                "\nОружие: " + weapon;
    }

    @Override
    public boolean equals(Object obj) {
        MilitarySatellite that = (MilitarySatellite) obj;
        return weapon.equals(that.weapon) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getWidth(), getLength(), weapon);
    }
}
