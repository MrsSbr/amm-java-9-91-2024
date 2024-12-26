package ru.vsu.amm.java;

// Задача про шагающего робота.
// Реализовать имитацию движения робота по следующим правилам
// один поток “ходит” левой ногой, второй правой ногой.
// Они должны делать это последовательно. Начать можно с любой ноги

import ru.vsu.amm.java.entity.Robot;

public class Main {
    public static void main(String[] args) {
        Robot robot = new Robot();
        robot.startWalking(true);
    }
}