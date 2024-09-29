package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.AmmoWarehouse;
import ru.vsu.amm.java.classes.BombWarehouse;
import ru.vsu.amm.java.classes.WarehouseImpl;
import ru.vsu.amm.java.enums.AmmoType;
import ru.vsu.amm.java.enums.BombType;

public class WarehouseDemo {
    public static void main(String[] args) {
        WarehouseImpl veresk = new AmmoWarehouse(
                "Вереск",
                "Кольцовская, 154",
                250000,
                AmmoType.UNIVERSAL_7_62
        );
        WarehouseImpl acacia = new BombWarehouse(
                "Акация",
                "Софьи Перовской, 58",
                5,
                BombType.FAB50
        );
        WarehouseImpl similarToAcacia = new BombWarehouse(
                "Акация",
                "Софьи Перовской, 58",
                8,
                BombType.FAB50
        );

        System.out.println(veresk + "\n");
        System.out.println(acacia + "\n");
        System.out.println("acacia == similarToAcacia: " + acacia.equals(similarToAcacia) + "\n");
        System.out.println("veresk.hashCode() == " + veresk.hashCode());
        System.out.println("acacia.hashCode() == " + acacia.hashCode());
        System.out.println("similarToAcacia.hashCode() == " + similarToAcacia.hashCode() + "\n");
        veresk.add(150000);
        System.out.println(veresk + "\n");
        similarToAcacia.account();
        System.out.println();
        acacia.empty();
    }
}