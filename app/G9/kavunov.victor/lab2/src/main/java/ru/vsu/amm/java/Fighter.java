package ru.vsu.amm.java;

import java.util.Objects;

public class Fighter extends MilitaryAircraft {
    private int numOfGuns;
    private int numOfBullets;

    public Fighter(String name, int maxSpeed, int fuelCapacity, int numOfGuns, int numOfBullets) {
        super(name, maxSpeed, fuelCapacity);
        this.numOfGuns = numOfGuns;
        this.numOfBullets = numOfBullets;
    }

    @Override
    public void attack() {
        int bulletConsumption = numOfGuns * 50;
        if (numOfBullets >= bulletConsumption) {
            numOfBullets -= bulletConsumption;
            System.out.println("Атака истребителя " + name + " выполнена успешно. Израсходовано " + bulletConsumption +
                    " патронов. Остаток " + numOfBullets);
        } else {
            System.out.println("!!! Атака истребителя " + name + " невозможна. Недостаточно патронов !!!");
        }
    }

    @Override
    public String toString() {
        return "Истребитель\nНазвание " + name + "\nМаксимальная скорость: " + maxSpeed +
                " м/c\nОбъём бака: " + fuelCapacity + " л\nКол-во пулемётов: " + numOfGuns +
                "\nЗапас патронов: " + numOfBullets + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Fighter that = (Fighter) o;
        return that.numOfGuns == numOfGuns && that.numOfBullets == numOfBullets;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numOfGuns, numOfBullets);
    }
}
