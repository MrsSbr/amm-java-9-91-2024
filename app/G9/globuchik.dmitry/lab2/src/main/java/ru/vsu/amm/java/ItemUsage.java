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
                "Стол деревянный",
                12500,
                5,
                80,
                120,
                120,
                "Серый",
                "Стол");

        Table.perform();
        System.out.print(Table);


        ItemImplementation Sofa = new Furniture(
                "Кресло босс",
                65600,
                3,
                120,
                240,
                180,
                "коричневый",
                "Кресло");
        Sofa.perform();
        System.out.print(Sofa);

        System.out.println(Sofa.equals(Table));
        System.out.println(Sofa.hashCode());

        defineType(Sofa);
        defineType(Table);
        defineType(Cola);
    }


    public static void defineType(ItemImplementation item) {
        if (item instanceof LiquidItem) {
            System.out.println("Это какая-то жидкость");
        } else if (item instanceof Furniture) {
            System.out.print("Это мебель");
        }
    }
}