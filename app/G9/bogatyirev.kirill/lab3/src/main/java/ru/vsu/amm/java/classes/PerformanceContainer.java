package ru.vsu.amm.java.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PerformanceContainer {
    private List<Plays> plays = new ArrayList<>();

    public PerformanceContainer(){
    }

    public PerformanceContainer(List<Plays> plays){
        this.plays = plays;
    }

    public int getSize() {
        return plays.size();
    }

    public List<Plays> getPlays() {
        return plays;
    }

    @Override
    public String toString() {
        return plays.stream()
                .map(play -> play.getName() + ", " + play.getCountTickets())
                .collect(Collectors.joining("\n"));
    }
}
