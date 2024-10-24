package ru.vsu.amm.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Interval
{
    private int start;
    private int end;

    public Interval(int start, int end)
    {
        this.start = start;
        this.end = end;
    }
    public Interval () {}

    void setStart(int a)
    {
        this.start = a;
    }
    void setEnd(int a)
    {
        this.end = a;
    }
    void set(int a,int b)
    {
        this.start = a;
        this.end = b;
    }
    int getStart()
    {
        return this.start;
    }
    int getEnd()
    {
        return this.end;
    }
    public boolean ifOverlapped(Interval a)
    {
        return (this.end >= a.start);
    }
    public static boolean ifOverlapped(Interval a, Interval b)
    {
        return (a.end >= b.start);
    }
    public static Interval Merge(Interval a, Interval b)
    {
        a.end = Math.max(a.end, b.end);
        return a;
    }
    public void Merge(Interval a)
    {
        this.end = Math.max(a.end, this.end);
    }
}

public class MergeIntervals {
    public static void main(String[] args) {
        List<Interval> intervals = initArray();
        List<Interval> result = Merge(intervals);

        print(result);
    }

    static List<Interval> Merge(List<Interval> intervals)
    {
        List<Interval> result = new ArrayList<>();
        Interval tmp = intervals.getFirst();

        for (int i = 1; i < intervals.size(); ++i) {
            if (Interval.ifOverlapped(tmp, intervals.get(i))) { // А как правильно вызывать методы класса? Через экземпляр класса или через сам класс?
                tmp.Merge(intervals.get(i)); // Через экземпляр класса
            } else {
                result.add(tmp);
                tmp = intervals.get(i);
            }
        }
        result.add(tmp);
        return result;
    }

    static void print(List<Interval> intervalList) {
        System.out.println("Result:");
        for (Interval elem : intervalList) {
            System.out.println("{" + elem.getStart() + " . " + elem.getEnd() + "}");
        }
    }

    static List<Interval> initArray() {
        int countSeries = 0;
        String input;
        List<Interval> result = new ArrayList<>(countSeries);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter count of intervals in initial array:");
            input = bufferedReader.readLine();
            countSeries = Integer.parseInt(input);
            if (countSeries > 10000 || countSeries < 0) {
                System.out.println("Error: count of intervals invalid");
            }

            for (int i = 1; i <= countSeries; ++i) {
                Interval tmpResult = new Interval();

                System.out.println("Enter " + i + " interval");
                input = bufferedReader.readLine();
                String[] tmpStr = input.split("\\s+");

                int start = Integer.parseInt(tmpStr[0]); // Можно все свернуть в Interval tmpResult = new Interval(Integer.parseInt(tmpStr[0]), Integer.parseInt(tmpStr[1]));
                int end = Integer.parseInt(tmpStr[1]);

                tmpResult.set(start, end);
                result.add(tmpResult); // result.add(new Interval(Integer.parseInt(tmpStr[0]), Integer.parseInt(tmpStr[1])); (Нечитабельно?)
            }
        } catch (Exception NumberFormatException) {
            System.out.println("Error: " + NumberFormatException);
        }

        return result;
    }
}