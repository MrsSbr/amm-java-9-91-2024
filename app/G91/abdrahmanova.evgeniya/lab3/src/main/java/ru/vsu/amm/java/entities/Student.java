package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enums.PerfomanceName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Student {
    private List<Perfomance> perfomanceChoice;

    public List<Perfomance> getPerfomanceChoice() {
        return perfomanceChoice;
    }

    public void setPerfomanceChoice(List<Perfomance> perfomanceChoice) {
        this.perfomanceChoice = perfomanceChoice;
    }

    public Student(List<Perfomance> choices) {
        perfomanceChoice = new ArrayList<>();
        Random random = new Random();
        int perfomanceCount = random.nextInt(PerfomanceName.values().length + 1);
        for (int i = 0; i < perfomanceCount; i++) {
            PerfomanceName perfomanceName = PerfomanceName.values()[random.nextInt(PerfomanceName.values().length)];
            perfomanceChoice.add(new Perfomance(perfomanceName));
        }
    }
}