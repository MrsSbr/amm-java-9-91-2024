package ru.vsu.amm.java;

import java.util.Date;

public class AncientRecord {
    private Date date;
    private int mammothWeight;
    private HunterName hunterName;

    public AncientRecord(Date date, int mammothWeight, HunterName hunterName) {
        this.date = date;
        this.mammothWeight = mammothWeight;
        this.hunterName = hunterName;
    }

    public Date getDate() {
        return date;
    }

    public HunterName getHunterName() {
        return hunterName;
    }

    public int getMammothWeight() {
        return mammothWeight;
    }
}
