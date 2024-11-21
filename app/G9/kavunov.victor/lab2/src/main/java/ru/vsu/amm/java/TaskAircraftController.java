package ru.vsu.amm.java;

public class TaskAircraftController {
    static boolean isBomber(MilitaryAircraft m) {
        return m instanceof Bomber;
    }

    public static void main(String[] args) {

        Bomber b1 = new Bomber(
                "Xian H-6K",
                292,
                32000,
                5);
        Bomber b2 = new Bomber(
                "Xian H-6K",
                292,
                32000,
                5);
        Bomber b3 = new Bomber(
                "Туполев Ту-22М",
                640,
                50000,
                8);
        Bomber b4 = new Bomber(
                "Туполев Ту-22М",
                640,
                50000,
                0);

        Fighter f1 = new Fighter(
                "Сухой Су-35",
                700,
                11500,
                1,
                150
        );
        Fighter f2 = new Fighter(
                "Сухой Су-35",
                700,
                11500,
                1,
                150
        );
        Fighter f3 = new Fighter(
                "МиГ-31",
                833,
                15500,
                2,
                260
        );
        Fighter f4 = new Fighter(
                "МиГ-31",
                833,
                15500,
                2,
                80
        );

        System.out.println("-----------------Демонстрация работы .toString для бомбардировщика------------------");
        System.out.println(b1);
        System.out.println("-----------------Демонстрация работы .toString для истребителя------------------");
        System.out.println(f1);

        System.out.println("-----------Демонстрация переопределения метода интерфейса-----------");
        b1.fly();
        System.out.println("-----------------------");
        f1.fly();

        System.out.println("----------Демонстрация переопределения публичного метода абстрактного класса--------");
        b3.attack();
        System.out.println("-----------------------");
        b4.attack();
        System.out.println("-----------------------");
        f3.attack();
        System.out.println("-----------------------");
        f4.attack();

        System.out.println("-----------Демонстрация работы .equals------------");
        System.out.println("b1 equals b2? " + b1.equals(b2));
        System.out.println("b2 equals b3? " + b2.equals(b3));
        System.out.println("b3 equals b4? " + b3.equals(b4));
        System.out.println("b1 equals f1? " + b1.equals(f1));

        System.out.println("-----------Демонстрация работы .hashCode------------");
        System.out.println("b1 hashCode? " + b1.hashCode());
        System.out.println("b2 hashCode? " + b2.hashCode());
        System.out.println("b3 hashCode? " + b3.hashCode());
        System.out.println("b4 hashCode? " + b4.hashCode());
        System.out.println("f1 hashCode? " + f1.hashCode());

        System.out.println("-----------Демонстрация работы instanseOf------------");
        System.out.println("b1 bomber? " + isBomber(b1));
        System.out.println("f1 bomber? " + isBomber(f1));
    }
}