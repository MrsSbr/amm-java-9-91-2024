package ru.vsu.amm.java;


import java.util.Arrays;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        var temperatures = new int[] {73, 74, 75, 71, 69, 72, 76, 73};
        //var temperatures = new int[] {30, 60, 90};
        //var temperatures = new int[] {30, 40, 50, 60};
        var answer = dailyTemperatures(temperatures);
        System.out.println(Arrays.toString(answer));
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        var answer = new int[temperatures.length];
        var st = new Stack<Integer>();
        for (int i = 0; i < temperatures.length; i++) {
            while (!st.isEmpty() && temperatures[i] > temperatures[st.peek()]) {
                var index = st.pop();
                answer[index] = i - index;
            }
            st.push(i);
        }
        return answer;
    }
}