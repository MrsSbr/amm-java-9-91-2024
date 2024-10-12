package ru.vsu.amm.java.enums;

public enum BombType {
    FAB50(25),
    FAB500(213),
    FAB5000(2207);

    public final int tntEquivalent;

    BombType(int tntEquivalent) {
        this.tntEquivalent = tntEquivalent;
    }
}
