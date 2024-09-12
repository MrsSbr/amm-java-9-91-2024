package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TaskBoatsToSavePeople {

    private static Scanner scanner = new Scanner(System.in);
    private static List<Integer> people = new ArrayList<>();
    private static int limit;


    private static int boatsCountingAlgorithm() {

        int boatsCount = 0;
        int smallestWeightIndex = 0;
        int biggestWeightIndex = people.size() - 1;

        people.sort(Comparator.reverseOrder());

        while (smallestWeightIndex < biggestWeightIndex) {

            if (people.get(smallestWeightIndex) + people.get(biggestWeightIndex) <= limit) {
                biggestWeightIndex--;
            }

            smallestWeightIndex++;
            boatsCount++;
        }

        return boatsCount;
    }


    public static void main(String[] args) {
        System.out.println("please enter PEOPLE array (num num num num)");
        String inputString = scanner.nextLine();
        String[] inputNumbers = inputString.split("[ ]+");

        for (String number : inputNumbers) {
            people.add(Integer.parseInt(number));
        }

        System.out.println("enter limit");
        limit = scanner.nextInt();
        System.out.println(boatsCountingAlgorithm());
    }
}