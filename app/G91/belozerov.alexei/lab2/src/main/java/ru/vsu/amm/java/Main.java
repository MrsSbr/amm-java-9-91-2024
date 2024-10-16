package ru.vsu.amm.java;

public class Main {
    public static void main(String[] args) {
        VesselImpl cargoShip1 = new CargoShip(
                "Грузовик-1",
                5000,
                CargoType.CONTAINER
        );
        cargoShip1.sail();
        CargoShip cargoShip2 = new CargoShip(
                "Грузовик-1",
                5000,
                CargoType.CONTAINER
        );
        cargoShip2.sail();
        CargoShip cargoShip3 = new CargoShip(
                "Грузовик-2",
                1000,
                CargoType.OIL
        );
        cargoShip3.sail();
        VesselImpl fishingBoat1 = new FishingBoat(
                "Рыбак-1",
                300,
                FishType.SARDINES
        );
        fishingBoat1.sail();

        System.out.println("\ninstanceof:");
        defineClass(cargoShip1);
        defineClass(fishingBoat1);

        System.out.println("\nПереопределение метода абстрактного класса:");
        System.out.println(cargoShip1.getVesselType());
        System.out.println(fishingBoat1.getVesselType());

        System.out.println("\nhashcode:");
        System.out.println(cargoShip1.hashCode());
        System.out.println(cargoShip2.hashCode());
        System.out.println(fishingBoat1.hashCode());

        System.out.println("\ntoString:");
        System.out.println(cargoShip1);
        System.out.println();
        System.out.println(fishingBoat1);

        System.out.println("\nequals:");
        System.out.println(cargoShip1.equals(cargoShip2));
        System.out.println(cargoShip1.equals(cargoShip3));
    }

    static void defineClass(VesselImpl vessel) {
        if (vessel instanceof CargoShip) {
            System.out.println(vessel.getName() + " имеет тип " + vessel.getVesselType());
        } else if (vessel instanceof FishingBoat) {
            System.out.println(vessel.getName() + " имеет тип " + vessel.getVesselType());
        }
    }
}