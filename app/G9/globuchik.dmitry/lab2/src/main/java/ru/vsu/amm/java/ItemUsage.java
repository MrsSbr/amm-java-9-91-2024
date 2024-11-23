package ru.vsu.amm.java;

public class ItemUsage {
    public static void main(String[] args) {


        ItemImplementation Cola = new LiquidItem(
                "Cola",
                150,
                26,
                500);
        Cola.perform();
        System.out.print(Cola);

        ItemImplementation Table = new Furniture(
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


        ItemImplementation Sofa = new Furniture(
                "Boss chair",
                65600,
                3,
                120,
                240,
                180,
                "Brown",
                "Chair");
        Sofa.perform();
        System.out.print(Sofa);

        System.out.println("Sofa == Table is: " + Sofa.equals(Table));
        System.out.println("Sofa hashcode: " + Sofa.hashCode());

        defineType(Sofa);
        defineType(Table);
        defineType(Cola);
    }


    public static void defineType(ItemImplementation item) {
        if (item instanceof LiquidItem) {
            System.out.println("This is Liquid\n");
        } else if (item instanceof Furniture) {
            System.out.print("This is Furniture\n");
        }
    }
}