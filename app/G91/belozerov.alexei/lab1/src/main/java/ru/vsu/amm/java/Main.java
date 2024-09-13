package ru.vsu.amm.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static int TaskRemoveDuplicatesFromSortedArray(int[] sortedArray) {
        int numberOfOriginElems = 1;
        for (int i = 0; i < sortedArray.length - 1; i++) {
            if (sortedArray[i] != sortedArray[i + 1]) {
                sortedArray[numberOfOriginElems] = sortedArray[i + 1];
                numberOfOriginElems++;
            }
        }
        return numberOfOriginElems;
    }

    public static void main(String[] args) {
        System.out.print("Введите элементы отсортированного массива через пробел\n --> ");
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            String[] strSortedArray = bf.readLine().split("\\s+");
            int[] sortedArray = new int[strSortedArray.length];
            for (int i = 0; i < strSortedArray.length; i++) {
                sortedArray[i] = Integer.parseInt(strSortedArray[i]);
            }
            System.out.println("numberOfOriginElems = "
                    + TaskRemoveDuplicatesFromSortedArray(sortedArray)
                    + ", " + "sortedArray = " + Arrays.toString(sortedArray));
        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}