package ru.vsu.amm.java;
import java.util.Scanner;

public class Palindrom {
    public static void main(String[] args) {
        System.out.println("Введите число: ");
        int result = new Scanner(System.in).nextInt();
        int reverse = 0;
        int number = result;
        while (result != 0) {
            int tmp = result % 10;
            reverse = reverse * 10 + tmp;
            result = result / 10;
        }
        if (reverse == number && number >= 0) {
            System.out.println("true");
        }
        else {
            System.out.println("false");
        }
    }
}