package ru.vsu.amm.java.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.vsu.amm.java.TheatrePerfomance;

public class Student {
    private List<Perfomance> perfomanceChoices;

    public List<Perfomance> getPerfomanceChoices() {
        return perfomanceChoices;
    }

    public Student() {
        perfomanceChoices = new ArrayList<>();
        Random random = new Random();
        int perfomanceCount = random.nextInt(TheatrePerfomance.NUMBER_PERFOMANCE + 1);
        for (int i = 0; i < perfomanceCount; i++) {
            int perfomanceNumber = random.nextInt(TheatrePerfomance.NUMBER_PERFOMANCE) + 1;
            perfomanceChoices.add(new Perfomance(perfomanceNumber));
        }
    }
}
