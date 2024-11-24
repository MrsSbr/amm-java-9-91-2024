package ru.vsu.amm.java;

public class TaskLongestMountain {

    public static int findLongestMountainLength(int[] arr) {
        int i = 0;
        int maxLength = 0;
        int length;
        if (arr.length >= 3){
            while (i < arr.length - 1) {
                if (arr[i] < arr[i + 1]) {
                    ++i;
                    length = 2;
                    while (i < arr.length - 1 && arr[i] < arr[i + 1]){
                        ++length;
                        ++i;
                    }
                    if (i < arr.length - 1 && arr[i] > arr[i + 1]) {
                        while (i < arr.length - 1 && arr[i] > arr[i + 1]){
                            ++length;
                            ++i;
                        }
                        if (maxLength < length) {
                            maxLength = length;
                        }
                        if (i < arr.length - 1 && arr[i] == arr[i + 1]) {
                            ++i;
                        }
                    }
                }
                else {
                    ++i;
                }
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        int[] arr1 = {2,1,4,7,3,2,5};
        System.out.println(findLongestMountainLength(arr1));

        int[] arr2 = {2,2,2};
        System.out.println(findLongestMountainLength(arr2));

        int[] arr3 = {2,3,4,5,6,7};
        System.out.println(findLongestMountainLength(arr3));

        int[] arr4 = {2,1,4,4,7,3,2,2,5};
        System.out.println(findLongestMountainLength(arr4));

        int[] arr5 = {2,1,4,7,7,3,2,5};
        System.out.println(findLongestMountainLength(arr5));
    }
}