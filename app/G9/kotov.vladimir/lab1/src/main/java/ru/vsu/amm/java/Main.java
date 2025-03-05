package ru.vsu.amm.java;

import java.util.Scanner;

public class Main {
    public static int findPeakIndex(int[] mountainArray) {
        int left = 0;
        int right = mountainArray.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (mountainArray[mid] < mountainArray[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the elements of the mountain array separated by spaces:");

        String inputLine = scanner.nextLine();
        String[] tokens = inputLine.trim().split("\\s+");
        int[] mountainArray = new int[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            mountainArray[i] = Integer.parseInt(tokens[i]);
        }

        int peakIndex = findPeakIndex(mountainArray);
        System.out.println("Peak index: " + peakIndex);

        scanner.close();
    }
}
