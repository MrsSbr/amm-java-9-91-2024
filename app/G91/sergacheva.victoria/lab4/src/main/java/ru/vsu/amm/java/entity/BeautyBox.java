package ru.vsu.amm.java.entity;

import java.time.LocalDate;
import java.util.List;

public class BeautyBox {

    private LocalDate date;

    private int countSold;

    private List<String> contents;

    public BeautyBox(LocalDate date, int countSold, List<String> contents) {
        this.date = date;
        this.countSold = countSold;
        this.contents = contents;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCountSold() {
        return countSold;
    }

    public List<String> getContents() {
        return contents;
    }
}
