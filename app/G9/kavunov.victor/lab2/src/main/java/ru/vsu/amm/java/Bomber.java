package ru.vsu.amm.java;

import java.util.Objects;

public class Bomber extends MilitaryAircraft {
    private int numOfBombs;

    public Bomber(String name, int maxSpeed, int fuelCapacity, int numOfBombs) {
        super(name, maxSpeed, fuelCapacity);
        this.numOfBombs = numOfBombs;
    }

    @Override
    public void attack() {
        if (numOfBombs > 0) {
            numOfBombs -= 1;
            System.out.println("Атака бомбардировщика " + name + " выполнена успешно. Запас боеголовок - " + numOfBombs);
        } else {
            System.out.println("!!! Атака бомбардировщика " + name + " невозможна. Отсутствуют боеголовки !!!");
        }
    }

    @Override
    public String toString() {
        return "Бомбардировщик\nНазвание " + name + "\nМаксимальная скорость: " + maxSpeed +
                " м/c\nОбъём бака: " + fuelCapacity + " л\nЗапас боеголовок:  " + numOfBombs;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Bomber that = (Bomber) o;
        return that.numOfBombs == numOfBombs;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numOfBombs);
    }
}
