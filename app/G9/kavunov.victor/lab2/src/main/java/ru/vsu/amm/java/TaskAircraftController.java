package ru.vsu.amm.java;

public class TaskAircraftController {
    static boolean isPlane(MilitaryAircraftImpl m) {
        return m instanceof MilitaryPlane;
    }
    public static void main(String[] args) {
        MilitaryPlane p1 = new MilitaryPlane(
                "Сухой Су-24 М",
                472,
                11700,
                4);
        MilitaryPlane p2 = new MilitaryPlane(
                "Туполев Ту-22М",
                640,
                50000,
                8);
        MilitaryPlane p3 = new MilitaryPlane(
                "Туполев Ту-22М",
                640,
                50000,
                0);

        MilitaryHelicopter h1 = new MilitaryHelicopter(
                "Белл AH-1 «Кобра»",
                78,
                1128,
                2,
                4000);
        MilitaryHelicopter h2 = new MilitaryHelicopter(
                "Ми-24",
                93,
                2130,
                4,
                1470);
        MilitaryHelicopter h3 = new MilitaryHelicopter(
                "Ми-24",
                93,
                2130,
                4,
                98);

        System.out.println("------------------------Демонстрация работы .toString для самолёта------------------------");
        System.out.println(p1);
        System.out.println("------------------------Демонстрация работы .toString для вертолёта------------------------");
        System.out.println(h1);

        System.out.println("-----------Демонстрация переопределения метода интерфейса------------");
        p1.makeFlight();

        System.out.println("-----------Демонстрация переопределения публичного метода абстрактного класса------------");
        p1.attack();
        System.out.println("-----------------------");
        h3.attack();

        System.out.println("-----------Демонстрация работы .equals------------");
        System.out.println("p1 equals p2? " + p1.equals(p2));
        System.out.println("p2 equals p3? " + p2.equals(p3));
        System.out.println("p1 equals h1? " + p3.equals(p1));

        System.out.println("-----------Демонстрация работы .hashCode------------");
        System.out.println("p1 hashCode? " + p1.hashCode());
        System.out.println("p2 hashCode? " + p2.hashCode());
        System.out.println("p3 hashCode? " + p3.hashCode());

        System.out.println("-----------Демонстрация работы instanseOf------------");
        System.out.println("p1 plane? " + isPlane(p1));
        System.out.println("h1 plane? " + isPlane(h1));
    }
}