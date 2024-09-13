package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TaskBoatsToSavePeople {

    private static int boatsCountingAlgorithm(List<Integer> people, int limit) {

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
        Scanner scanner = new Scanner(System.in);
        List<Integer> people = new ArrayList<>();
        int limit = 0;

        System.out.println("please enter PEOPLE array (num num num num)");
        String inputString = scanner.nextLine();
        String[] inputNumbers = inputString.split("\\s+");

        try {
            for (String number : inputNumbers) {
                people.add(Integer.parseInt(number));
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("enter limit");
        limit = scanner.nextInt();

        System.out.println(boatsCountingAlgorithm(people, limit));
    }
}
