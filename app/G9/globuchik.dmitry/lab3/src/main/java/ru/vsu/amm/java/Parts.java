package ru.vsu.amm.java;

import java.util.List;

public class Parts {

    private static final int PARTS_COUNT = 1570;

    public static void main(String[] args) {
        DetailsList list = new DetailsList(PARTS_COUNT);
        list.print();
    }
}
