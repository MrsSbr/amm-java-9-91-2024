package ru.vsu.amm.java;

public class Main {
    public static void main(String[] args) {
        CargoShip cargoShip = new CargoShip("Ocean Giant", 200, 10000);
        Tanker tanker = new Tanker("Oil King", 300, 500000);
        Tugboat tugboat = new Tugboat("Mighty Tug", 50, 2000);

        cargoShip.displayInfo();
        tanker.displayInfo();
        tugboat.displayInfo();

        System.out.println("Cargo Ship Crew Size: " + cargoShip.getCrewSize());
        System.out.println("Tanker Crew Size: " + tanker.getCrewSize());
        System.out.println("Tugboat Crew Size: " + tugboat.getCrewSize());

        if(tanker instanceof Navigable){
            System.out.println("Tanker is navigable");
            ((Navigable)
            tanker).navigateTo("34.56N, 123.45W");
        }
    }
}