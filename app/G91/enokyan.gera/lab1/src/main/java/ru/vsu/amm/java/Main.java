package ru.vsu.amm.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    private static int findMountainPeakIndex(int[] mountainArray) {
        int left = 0, right = mountainArray.length - 1, middle = 0;
        while (left < right) {
            middle = (left + right) / 2;
            if (mountainArray[middle] < mountainArray[middle + 1]) {
                left = middle;
            } else if (mountainArray[middle] < mountainArray[middle - 1]) {
                right = middle;
            } else {
                return middle;
            }
        }
        return middle;
    }

    public static void main(String[] args) {
        System.out.print("Enter the elements of mountain array in one row dividing them by space(-s):\n");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String[] mountainArray = br.readLine().trim().split("\\s+");
            int[] mountainIntArray = new int[mountainArray.length];
            for (int i = 0; i < mountainArray.length; i++) {
                mountainIntArray[i] = Integer.parseInt(mountainArray[i]);
            }
            System.out.println("Index of peak is " + findMountainPeakIndex(mountainIntArray));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}