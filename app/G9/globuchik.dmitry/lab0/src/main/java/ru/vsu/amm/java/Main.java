package ru.vsu.amm.java;

public class Main {
    public static void main(String[] args) {
        int before = 0, current = 1;
        for (int i = 1; i <= 10; ++i) {
            System.out.print(i);
            if (i <= 3) {
                if (i == 1) {
                    System.out.print("-st ");
                } else if (i == 2) {
                    System.out.print("-nd ");
                } else {
                    System.out.print("-rd ");
                }
            }
            else
                System.out.print("-th ");
            System.out.print("Fibonacci number is: " + (before + current) + '\n');
            int tmp = before + current;
            before = current;
            current = tmp;
        }
    }
}