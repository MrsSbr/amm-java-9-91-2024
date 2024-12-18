package ru.vsu.amm.java.enums;

public enum DrinkName {
    ESPRESSO,
    CAPPUCHINO,
    LATTE,
    AMERICANO,
    MOCCHA;

    public static boolean isValidDrinkName(String name) {
       if (name == null || name.isBlank()) return false;
        try {
            DrinkName.valueOf(name.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
