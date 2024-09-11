package ru.vsu.amm.java;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < i; ++j) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}