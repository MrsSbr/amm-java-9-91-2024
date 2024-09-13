package ru.vsu.amm.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter array elements divided by space:");
            String[] plantsStr = bufferedReader.readLine().split(" +");
            int[] plants = new int[plantsStr.length];
            for (int i = 0; i < plants.length; i++) {
                plants[i] = Integer.parseInt(plantsStr[i]);
            }
            System.out.println("Enter watering can capacity:");
            int capacity = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Amount of steps: " + wateringPlants(plants, capacity));

        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    private static int wateringPlants(int[] plants, int capacity) {
        int steps = 0;
        int waterVolume = capacity;
        for (int i = 0; i < plants.length; i++) {
            waterVolume -= plants[i];
            steps++;
            if (i < plants.length - 1 && waterVolume < plants[i + 1]) {
                steps += (i + 1) * 2;
                waterVolume = capacity;
            }
        }
        return steps;
    }
}