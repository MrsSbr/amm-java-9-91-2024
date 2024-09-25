package ru.vsu.amm.java;

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
        System.out.println(acacia.hashCode());
        System.out.println(similarToAcacia.hashCode() + "\n");
        veresk.add(150000);
        System.out.println(veresk + "\n");
        similarToAcacia.account();
        System.out.println();
        acacia.empty();
    }
}