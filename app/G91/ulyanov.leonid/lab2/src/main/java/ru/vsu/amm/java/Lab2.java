package ru.vsu.amm.java;

public class Lab2 {
    public static void instanceOfTest(MilitaryVehicle vehicle) {
        if (vehicle instanceof Tank) {
            System.out.println("This is a tank.");
        } else if (vehicle instanceof Airplane) {
            System.out.println("This is an airplane.");
        } else {
            System.out.println("This is an unknown vehicle.");
        }
    }

    public static void encapsulationTest(MilitaryVehicle vehicle) {
        System.out.println("Speed:\t" + vehicle.getSpeed());
        System.out.println("Capacity:\t" + vehicle.getCapacity());
    }

    public static void polymorphismTest() {
        MilitaryVehicle test = new Tank("test", 0, 0, 0, "test");
        System.out.println(test);
        test = new Airplane("test", 0, 0, 0);
        System.out.println(test);
    }

    public static void main(String[] args) {
        Tank tank = new Tank("Some armament", 3, 10, 125, "iron");
        Airplane airplane = new Airplane("Some armament", 2, 80, 10);

        System.out.println("----------");
        System.out.println(tank);
        System.out.println(airplane);

        System.out.println("----------");
        instanceOfTest(tank);
        System.out.println("----------");
        encapsulationTest(airplane);
        System.out.println("----------");
        polymorphismTest();
    }
}