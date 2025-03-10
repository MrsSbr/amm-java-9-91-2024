package ru.vsu.amm.java;

import ru.vsu.amm.java.abstracts.AbstractCosmetic;
import ru.vsu.amm.java.classes.Lipstick;
import ru.vsu.amm.java.classes.Sculptor;
import ru.vsu.amm.java.enums.Brand;
import ru.vsu.amm.java.enums.Color;
import ru.vsu.amm.java.enums.Texture;

public class CosmeticApplication {
    public static void instanceOfTest(AbstractCosmetic cosmetic){
        if (cosmetic instanceof Lipstick) {
            System.out.println("Lipstick!");
        }
        else if (cosmetic instanceof Sculptor) {
            System.out.println("Sculptor!");
        }
        else {
            System.out.println("Unknown!");
        }
    }
    public static void equalsTest(AbstractCosmetic c1,AbstractCosmetic c2) {
        if (c1.equals(c2)) {
            System.out.println("Similar!");
        }
        else {
            System.out.println("Different!");
        }
    }
    public static void main(String[] args){
        Lipstick lipstick = new Lipstick(Brand.Dior,3500.25, Color.Pink);
        Lipstick lipstick2 = new Lipstick(Brand.Dior,3500.25,Color.Pink);
        Lipstick lipstick3 = new Lipstick(Brand.Dior,3500.25,Color.Red);
        Sculptor sculptor = new Sculptor(Brand.Channel, 6000, Texture.Liquid);
        System.out.println(lipstick);
        System.out.println("----------");
        System.out.println(sculptor);
        System.out.println("----------");
        instanceOfTest(lipstick);
        System.out.println("----------");
        instanceOfTest(sculptor);
        System.out.println("----------");
        equalsTest(lipstick,lipstick2);
        System.out.println("----------");
        equalsTest(lipstick,sculptor);
        System.out.println("----------");
        equalsTest(lipstick,lipstick3);
    }
}