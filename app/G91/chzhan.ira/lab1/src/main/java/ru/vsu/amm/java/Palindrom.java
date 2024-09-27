package ru.vsu.amm.java;
import java.util.Scanner;

public class Palindrom {
    public static void main(String[] args) {
        System.out.println("Введите число: ");
        int number = new Scanner(System.in).nextInt();

        if (isPalindrome(number)) {
            System.out.println("true");
        } else {
            System.out.println("false!");
        }
    }

    public static boolean isPalindrome(int number) {
        if (number < 0) {
            return false;
        }

        int reverse = 0;
        int originalNumber = number;

        while (number != 0) {
            int tmp = number % 10;
            reverse = reverse * 10 + tmp;
            number = number / 10;
        }

        return reverse == originalNumber;
    }
}