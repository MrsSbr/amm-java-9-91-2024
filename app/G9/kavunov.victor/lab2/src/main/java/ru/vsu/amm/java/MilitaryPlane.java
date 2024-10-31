package ru.vsu.amm.java;

public class MilitaryPlane extends MilitaryAircraftImpl {
    private int numOfBombs;
    public MilitaryPlane(String name, int maxSpeed, int fuelCapacity, int numOfBombs) {
        super(name, maxSpeed, fuelCapacity);
        this.numOfBombs = numOfBombs;
    }
    @Override
    public String toString() {
        return "Военный вертолёт\nНазвание " + name + "\nМаксимальная скорость: " + maxSpeed +
                " м/c\nОбъём бака: " + fuelCapacity + " л\nЗапас боеголовок:  " + numOfBombs;
    }
    @Override
    public void attack(){
        if(numOfBombs > 0) {
            numOfBombs -= 1;
            System.out.println("Атака самолёта выполнена успешно. Запас боеголовок - " + numOfBombs);
        }
        else {
            System.out.println("!!! Атака самолёта невозможна. Отсутствуют боеголовки !!!");
        }
    }
}
