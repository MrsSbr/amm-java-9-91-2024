package ru.vsu.amm.java;

import java.util.Scanner;

import static ru.vsu.amm.java.CountOfMatcheslnTournament.countMatches;

public class TaskCountOfMatcheslnTournament {
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
        System.out.print("Введите количество команд: ");
        int n = sc.nextInt();
        if (n<1 || n>200){
            System.out.println("Некорректное количество команд. Введите число от 1 до 200");
            return;
        }
        int matches = countMatches(n);
        System.out.println(matches);
        sc.close();
    }
}