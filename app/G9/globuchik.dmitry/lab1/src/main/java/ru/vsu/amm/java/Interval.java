package ru.vsu.amm.java;

public class Interval {
    private int start;
    private int end;

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Interval() {
    }

    void set(int a, int b) {
        this.start = a;
        this.end = b;
    }

    int getStart() {
        return this.start;
    }

    int getEnd() {
        return this.end;
    }

    public static boolean ifOverlapped(ru.vsu.amm.java.Interval a, ru.vsu.amm.java.Interval b) {
        return (a.end >= b.start);
    }

    public static ru.vsu.amm.java.Interval Merge(ru.vsu.amm.java.Interval a, ru.vsu.amm.java.Interval b) {
        a.end = Math.max(a.end, b.end);
        return a;
    }
}
