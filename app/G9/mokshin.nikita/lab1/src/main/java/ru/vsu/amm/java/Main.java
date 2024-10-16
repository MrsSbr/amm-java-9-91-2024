package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean correctInput = false;
        int n = 0;
        while (!correctInput) {
            System.out.println("Введите целое число n: ");

            try {
                n = Integer.parseInt(scanner.nextLine());
                if(n > 0) {
                    correctInput = true;
                } else {
                    System.out.println("Некорректный ввод. (n > 0)");
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод!");
            }
        }
        int[] arr = IntStream.range(1, n+1).toArray();
        int result = taskEliminationGame(arr);
        System.out.printf("Ответ: %d", result);
    }

    private static int taskEliminationGame(int[] arr) {
        List<Integer> list = new ArrayList<>(Arrays.stream(arr).boxed().toList());
        boolean startLeft = true;
        while (list.size() > 1) {
            if (startLeft) {
                for(int i = 0; i < list.size(); i++) {
                    list.remove(i);
                }
                startLeft = false;
            } else {
                for(int i = list.size() - 1; i >= 0; i-=2) {
                    list.remove(i);
                }
                startLeft = true;
            }
        }
        return list.get(0);
    }
}