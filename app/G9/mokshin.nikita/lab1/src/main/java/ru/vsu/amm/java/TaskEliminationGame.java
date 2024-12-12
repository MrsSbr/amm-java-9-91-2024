package ru.vsu.amm.java;

import java.util.*;

public class TaskEliminationGame {
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
        List<Integer> list = new LinkedList<>();
        for (int i = 1; i < n+1; i++) {
            list.add(i);
        }
        int result = taskEliminationGame(list);
        System.out.printf("Ответ: %d", result);
    }

    private static int taskEliminationGame(List<Integer> list) {
        boolean startLeft = true;
        while (list.size() > 1) {
            if (startLeft) {
                int sizeList = list.size();
                int i = 0;
                while(i < sizeList) {
                    list.remove(i);
                    i++;
                    sizeList--;
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