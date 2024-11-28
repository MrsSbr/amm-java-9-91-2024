package ru.vsu.amm.java.enums;

public enum Rating {
    one(1),
    two(2),
    three(3),
    four(4),
    five(5);

    public final int value;

    Rating(int value) {
        this.value = value;
    }
}
