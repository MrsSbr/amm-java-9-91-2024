package ru.vsu.amm.java;

public class ItemUsage {
    public static void main(String[] args) {
        LiquidItem Cola = new LiquidItem(
                "Cola",
                150,
                26,
                500);
        Cola.perform();
        System.out.print(Cola);
        Cola.pour(100);
        System.out.print(Cola);

        Item Table = new Furniture(
                "Wooden Table",
                12500,
                5,
                80,
                120,
                120,
                "Gray",
                "Table");

        Table.perform();
        System.out.print(Table);

        Furniture Sofa = new Furniture(
                "Boss chair",
                65600,
                3,
                120,
                240,
                180,
                "Brown",
                "Chair");
        System.out.print("----------\n");
        Sofa.perform();
        System.out.print(Sofa);
        System.out.print("----------\n");
        Sofa.build();
        System.out.print(Sofa);
        System.out.print("----------\n");
        Sofa.disassemble();
        System.out.print(Sofa);
        System.out.print("----------\n");

        System.out.println("Sofa == Table is: " + Sofa.equals(Table));
        System.out.println("Sofa hashcode: " + Sofa.hashCode());

        defineType(Sofa);
        defineType(Table);
        defineType(Cola);
    }


    public static void defineType(Item item) {
        if (item instanceof LiquidItem) {
            System.out.println("This is Liquid\n");
        } else if (item instanceof Furniture) {
            System.out.print("This is Furniture\n");
        }
    }
}