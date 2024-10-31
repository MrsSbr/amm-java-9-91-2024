package ru.vsu.amm.java;

public class MilitaryHelicopter extends MilitaryAircraftImpl {
    private int numOfGuns;
    private int numOfBullets;
    public MilitaryHelicopter(String name, int maxSpeed, int fuelCapacity, int numOfGuns, int numOfBullets) {
        super(name, maxSpeed, fuelCapacity);
        this.numOfGuns = numOfGuns;
        this.numOfBullets = numOfBullets;
    }
    @Override
    public String toString() {
        return "Военный вертолёт\nНазвание " + name + "\nМаксимальная скорость: " + maxSpeed +
                " м/c\nОбъём бака: " + fuelCapacity + " л\nКол-во пулемётов: " + numOfGuns +
                "\nЗапас патронов: " + numOfBullets + '\n';
    }
    @Override
    public void attack(){
        if(numOfBullets < 100) {
            System.out.println("!!! Атака вертолёта невозможна. Недостаточно патронов !!!");
        }
        else {
            numOfBullets -= 100;
            System.out.println("Атака вертолёта выполнена успешно. Запас патронов - " + numOfBullets);
        }
    }
}
