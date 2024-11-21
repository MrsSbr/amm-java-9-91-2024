package ru.vsu.amm.java.enums;

public enum DrinkName {
    ESPRESSO,
    CAPPUCHINO,
    LATTE,
    AMERICANO,
    MOCCHA;

    public static boolean isValidDrinkName(String name) {
        try {
            DrinkName.valueOf(name.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
