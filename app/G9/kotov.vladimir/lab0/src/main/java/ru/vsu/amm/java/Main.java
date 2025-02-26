package ru.vsu.amm.java;

public class Main {
    public static void main(String[] args) {
        int a = 2;
        int b = 3;
        int expectedSum = 5;
        int actualSum = a + b;

        if (actualSum == expectedSum) {
            System.out.println(a + " + " + b + " = " + expectedSum);
        } else {
            System.out.println(expectedSum + " ----- " + actualSum);
        }
    }
}