package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DetailsList {

    private List<Part> parts;

    public DetailsList(int PARTS_COUNT) {
        this.parts = new ArrayList<Part>(PARTS_COUNT);
        Random rand = new Random();
        for (int i = 0; i < PARTS_COUNT; i++) {
            parts.add(new Part(DetailTypes.getRandomType()));
        }
    }

    public void print() {
        for (Part part : parts) {
            System.out.println(part + " "+ part.getType());
        }
    }
}
