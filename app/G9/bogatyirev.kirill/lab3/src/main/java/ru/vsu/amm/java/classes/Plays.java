package ru.vsu.amm.java.classes;

import java.util.Objects;

public class Plays {
    private String name;
    private int countTickets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plays plays = (Plays) o;
        return countTickets == plays.countTickets && Objects.equals(name, plays.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, countTickets);
    }

    public Plays(String name, int countTickets) {
        this.name = name;
        this.countTickets = countTickets;
    }

    public String getName() {
        return name;
    }

    public int getCountTickets() {
        return countTickets;
    }
}
