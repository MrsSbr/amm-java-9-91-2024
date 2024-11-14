package ru.vsu.amm.java;

public class BettaFish extends Fish {
    private int aggressionLevel;

    public BettaFish(String name, int aggressionLevel) {
        super(name, "red");
        this.aggressionLevel = aggressionLevel;
    }

    public int getAggressionLevel() {
        return aggressionLevel;
    }

    @Override
    public void speak() {
        System.out.println("I am a betta fish!");
    }

    @Override
    public String toString() {
        return super.toString() + " My aggression level is " + aggressionLevel + '.';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && ((BettaFish) obj).aggressionLevel == aggressionLevel;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + aggressionLevel;
    }
}