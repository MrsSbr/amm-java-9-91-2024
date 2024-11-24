package ru.vsu.amm.java;

import ru.vsu.amm.java.checks.ArrayLimitations;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class TaskContainsDuplicate {
    public static boolean hasDuplicateElements(int[] numbers) {
        Set<Integer> uniqueElements = new HashSet<>();
        for (int number : numbers) {//o(n)
            if (!uniqueElements.contains(number)) {
                uniqueElements.add(number);
            } else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            ArrayLimitations arrayLimitations = new ArrayLimitations();

            System.out.print("Enter the length of the array: ");
            int arrayLength = input.nextInt();
            arrayLimitations.checkArrayLengthOrThrow(arrayLength);

            int[] nums = new int[arrayLength];

            for (int i = 0; i < arrayLength; ++i) {
                System.out.printf("Enter next element %d: ", i + 1);
                int element = input.nextInt();
                arrayLimitations.checkArrayElementValueOrThrow(element);
                nums[i] = element;
            }

            System.out.print("Input: [");
            for (int i = 0; i < nums.length; ++i) {
                System.out.print(nums[i] + (i == nums.length-1 ? "]" : ", "));
            }

            System.out.print("\nOutput: " + hasDuplicateElements(nums));
        } catch (IllegalArgumentException | InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }
}