package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enums.PerfomanceName;

public class Perfomance {
    private PerfomanceName name;

    public Perfomance(PerfomanceName name) {
        this.name = name;
    }

    public PerfomanceName getName() {return name;}

    public static Perfomance[] values() {
        return new Perfomance[] {
                new Perfomance(PerfomanceName.NIGTH_BEFORE_CHRISTMAS),
                new Perfomance(PerfomanceName.SHELCUNCHIC),
                new Perfomance(PerfomanceName.HAMLET),
                new Perfomance(PerfomanceName.ALICE_IN_WONDERLAND),
                new Perfomance(PerfomanceName.ROMEO_AND_JULIET),
                new Perfomance(PerfomanceName.MASTER_AND_MARGARET),
                new Perfomance(PerfomanceName.SILVER_HOOF),
                new Perfomance(PerfomanceName.SECRET_ROOM),
                new Perfomance(PerfomanceName.TWELFTH_CHEER),
                new Perfomance(PerfomanceName.DUKE_IGOR)
        };
    }
}
