package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Sacrifice;

import java.util.ArrayList;

public final class ArrayListOfSacrificeFactory {
    private ArrayListOfSacrificeFactory() {
    }

    public static ArrayList<Sacrifice> generateSacrifices(int n) {
        ArrayList<Sacrifice> res = new ArrayList<>(n);

        for (int i = 0; i < n; ++i) {
            res.add(SacrificeFactory.generateSacrifice());
        }

        return res;
    }
}
