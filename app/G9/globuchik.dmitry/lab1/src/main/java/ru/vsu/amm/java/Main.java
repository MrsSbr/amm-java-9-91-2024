package ru.vsu.amm.java;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        /* Можно было сделать и с обычными массивами(динамическими) как минимум для изначальных массивов, но я решил все делать в единообразном виде)
         * int[] intervals = initArray();
         * ...
         * static int[] initArray(){
         * int[] result = null;
         * ...
         * result = new int[n];
         * }
         * */
        ArrayList<ArrayList<Integer>> intervals = initArray();
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> tmp = intervals.getFirst();

        for (int i = 1; i < intervals.size(); ++i) {
            if (intervals.get(i).get(0) <= tmp.get(1)) {
                int k = (tmp.get(1) > intervals.get(i).get(1)) ? tmp.get(1) : intervals.get(i).get(1);
                tmp.set(1, k);
            } else {
                result.add(tmp);
                tmp = intervals.get(i);
            }
        }

        result.add(tmp);

        print(result);
    }

    static void print(ArrayList<ArrayList<Integer>> arrayLists) {
        System.out.println("Result:");
        for (ArrayList<Integer> elem : arrayLists) {
            System.out.println(elem);
        }
    }

    static ArrayList<ArrayList<Integer>> initArray() {
        int countSeries = 0;
        String input;

        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        System.out.println("Enter count of intervals in initial array:");
        try {
            input = bufferedReader.readLine();
            countSeries = Integer.parseInt(input);
            if (countSeries > 10000 || countSeries < 0) {
                System.out.println("Error: count of intervals invalid");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>(countSeries);

        for (int i = 1; i <= countSeries; ++i) {
            System.out.println("Enter " + i + " serie");
            try {
                input = bufferedReader.readLine();
                String[] tmpStr = input.split("\\s+");
                ArrayList<Integer> tmpResult = new ArrayList<Integer>();
                tmpResult.add(Integer.parseInt(tmpStr[0]));
                tmpResult.add(Integer.parseInt(tmpStr[1]));
                result.add(tmpResult);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }

        return result;
    }
}