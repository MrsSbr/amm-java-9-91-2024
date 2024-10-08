package ru.vsu.amm.java;

import ru.vsu.amm.java.Classes.FurnitureImpl;
import ru.vsu.amm.java.Classes.Chair;
import ru.vsu.amm.java.Classes.Sofa;

public class Demonstration {

    public static String instanceofCheck(FurnitureImpl furnitureImpl) {
        return furnitureImpl instanceof Chair ? "Chair" : "Sofa";
    }

    public static void main(String[] args) {

        FurnitureImpl chair1 = new Chair(
                "Венский",
                "Кожа",
                1199.99,
                true,
                4);

        FurnitureImpl chair2 = new Chair(
                "Комфорт",
                "Жаккард",
                2520.49,
                false,
                3);

        FurnitureImpl chair3 = new Chair(
                "Венский",
                "Кожа",
                1199.99,
                false,
                4);

        FurnitureImpl sofa1 = new Sofa(
                "Романс",
                "Синтепон",
                17900.00,
                false,
                2);

        System.out.print("\n" + chair1 + "\n\n");

        chair1.move();
        chair1.placeOnShowcase();
        chair1.removeFromShowcase();
        System.out.print("\n" + chair1 + "\n\n");

        chair2.removeFromShowcase();

        System.out.print("\n");
        System.out.println("chair1.equals(chair2): " + chair1.equals(chair2));
        System.out.println("chair1.equals(chair3): " + chair1.equals(chair3));

        System.out.print("\n" + sofa1 + "\n\n");
        sofa1.move();
        sofa1.removeFromShowcase();
        sofa1.placeOnShowcase();
        System.out.print("\n" + sofa1 + "\n\n");

        System.out.println("chair1.hashCode(): " + chair1.hashCode());
        System.out.println("chair3.hashCode(): " + chair3.hashCode());
        System.out.println("sofa1.hashCode(): " + sofa1.hashCode());

        System.out.print("\n");
        System.out.println("chair1 is instance of " + instanceofCheck(chair1));
        System.out.println("chair2 is instance of " + instanceofCheck(chair2));
        System.out.println("sofa1 is instance of " + instanceofCheck(sofa1));
    }
}