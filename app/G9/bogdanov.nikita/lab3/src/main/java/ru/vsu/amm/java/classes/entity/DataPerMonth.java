package ru.vsu.amm.java.classes.entity;

import java.time.Month;

public class DataPerMonth {
    private Month month;
    private int mele;
    private int female;

    public DataPerMonth(Month month, int male, int female) {
        this.month = month;
        this.mele = male;
        this.female = female;
    }

    public Month getMonth() {
        return month;
    }

    public int getMele() {
        return mele;
    }

    public int getFemale() {
        return female;
    }
}
