package ru.vsu.amm.java;

import java.util.Objects;

public class LiquidItem extends Item implements Liquid {
    private int volume;
    private int currVolume;

    public LiquidItem(String name,
                      int price,
                      int quantity,
                      int volume) {
        super(name, price, quantity);
        this.volume = volume;
        this.currVolume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public void pour(int amount) {
        if (amount <= 0) {
            System.out.println("You can't pour negative amount");
        } else if (amount > currVolume) {
            System.out.println("You can't pour more than curr volume");
        } else {
            currVolume -= amount;
            System.out.println("You pour " + amount + " oz. of " + getName());
        }
    }

    @Override
    public void perform() {
        System.out.println("Bottle " + getName() + " volume " + getVolume() + '\n');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LiquidItem that = (LiquidItem) o;
        return volume == that.volume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume);
    }

    @Override
    public String toString() {
        return "Bottle:\n" +
                "Max volume is " + volume + " oz.\n" +
                "Curr volume is " + currVolume + " oz.\n" +
                super.toString() + '\n';
    }
}