package ru.vsu.amm.java;

public class MilitarySatellite extends Satellite {

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

    public String getWeapon() {
        return weapon;
    }

    @Override
    public String toString() {
        return "Военный спутник\n" +
                super.toString() +
                "\nОружие: " + weapon;
    }
}
