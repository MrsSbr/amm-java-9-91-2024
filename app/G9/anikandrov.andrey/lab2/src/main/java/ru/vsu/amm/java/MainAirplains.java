package ru.vsu.amm.java;

public class MainAirplains {
    public static void main(String[] args) {
        Bomber Cy24 = new Bomber("Су 24", 1300, 32000);
        Spy  HeinkelHe45 = new Spy("Heinkel He 45", 290, 6, false);
        Fighter MesserschmittBf109 = new Fighter("Messerschmitt Bf 109", 550, 3);

        Cy24.flySpeed();
        HeinkelHe45.flySpeed();
        MesserschmittBf109.flySpeed();
        System.out.println("\n");

        Spy Orion = new Spy("Orion", 120, 9, true);
        Bomber SecondCy24 = new Bomber("Су 24", 1300, 32000);

        System.out.println("is Heinkel equals Orion? > " + HeinkelHe45.equals(Orion) );
        System.out.println("is Cy24 equals another Cy24? > " + Cy24.equals(SecondCy24) + "\n");

        System.out.println(Cy24.mission());
        System.out.println(Orion.mission() + "\n");
    }
}
