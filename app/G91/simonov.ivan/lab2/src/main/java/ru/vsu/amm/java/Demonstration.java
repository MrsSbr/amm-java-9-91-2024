package ru.vsu.amm.java;

import ru.vsu.amm.java.Classes.Furniture;
import ru.vsu.amm.java.Classes.Chair;
import ru.vsu.amm.java.Classes.Sofa;
import ru.vsu.amm.java.Enums.Material;

public class Demonstration {

    private static String instanceofCheck(Furniture furnitureImpl) {
        return furnitureImpl instanceof Chair ? "Chair" : "Sofa";
    }

    public static void main(String[] args) {

        Furniture chair1 = new Chair(
                "Венский",
                Material.Leather,
                1199.99,
                true,
                4);

        Furniture chair2 = new Chair(
                "Комфорт",
                Material.Jacquard,
                2520.49,
                false,
                3);

        Furniture chair3 = new Chair(
                "Венский",
                Material.Leather,
                1199.99,
                false,
                4);

        Furniture sofa1 = new Sofa(
                "Романс",
                Material.Sintepon,
                17900.00,
                false,
                2);

        System.out.print("\n" + chair1 + "\n\n");
        chair1.move();
        chair1.placeOnShowcase();
        chair1.removeFromShowcase();
        System.out.print("\n" + chair1 + "\n\n");

        chair2.removeFromShowcase();

        System.out.println("\nchair1.equals(chair2): " + chair1.equals(chair2));
        System.out.println("chair1.equals(chair3): " + chair1.equals(chair3));
        System.out.println("chair1.equals(sofa1): " + chair1.equals(sofa1));
        System.out.println("sofa1.equals(null): " + sofa1.equals(null));

        System.out.print("\n" + sofa1 + "\n\n");
        sofa1.move();
        sofa1.removeFromShowcase();
        sofa1.placeOnShowcase();
        System.out.print("\n" + sofa1 + "\n\n");

        System.out.println("chair1.hashCode(): " + chair1.hashCode());
        System.out.println("chair3.hashCode(): " + chair3.hashCode());
        System.out.println("chair2.hashCode(): " + chair2.hashCode());
        System.out.println("sofa1.hashCode(): " + sofa1.hashCode());

        System.out.println("\nchair1 is instance of " + instanceofCheck(chair1));
        System.out.println("chair2 is instance of " + instanceofCheck(chair2));
        System.out.println("sofa1 is instance of " + instanceofCheck(sofa1));
    }
}