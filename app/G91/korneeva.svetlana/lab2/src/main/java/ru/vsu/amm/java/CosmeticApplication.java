package ru.vsu.amm.java;

public class CosmeticApplication {
    public static void instanceOfTest(AbstractCosmetic cosmetic){
        if (cosmetic instanceof Lipstick)
            System.out.println("Lipstick!");
        else if (cosmetic instanceof Sculptor)
            System.out.println("Sculptor!");
        else
            System.out.println("Unknown!");
    }
    public static void equalsTest(AbstractCosmetic c1,AbstractCosmetic c2) {
        if (c1.equals(c2))
            System.out.println("Similar!");
        else
            System.out.println("Different!");
    }
    public static void main(String[] args){
        Lipstick lipstick = new Lipstick("Dior",3500.25,"Pink");
        Lipstick lipstick2 = new Lipstick("Dior",3500.25,"Pink");
        Sculptor sculptor = new Sculptor("Channel", 6000, "Liquid");
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
    }
}