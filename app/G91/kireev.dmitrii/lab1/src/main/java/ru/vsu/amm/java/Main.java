package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    static final List<Integer> people = new ArrayList<>();
    static int limit;


    public static int task() {

        int boatsCount = 0;
        int i = 0;
        int length = people.size();

        people.sort(null);

        while ((i < length - 1) && (people.get(i) + people.get(i + 1) <= limit)) {
            boatsCount += 1;
            i += 2;
        }

        if (i >= length) return boatsCount;

        boatsCount += (length - i);
        return boatsCount;
    }

    public static void init() {

        System.out.println("please enter PEOPLE array (num num num num)");
        String inputString = scanner.nextLine();
        String[] inputNumbers = inputString.split(" ");

        for (String number : inputNumbers) {
            people.add(Integer.parseInt(number));
        }

        System.out.println("enter limit");
        limit = scanner.nextInt();
    }

    public static void main(String[] args) {
        init();
        System.out.println(task());
    }
}