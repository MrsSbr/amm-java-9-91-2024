package ru.vsu.amm.java;

public class Tanker extends WaterVessel implements Navigable{
    private double oilCapacity;

    public Tanker(String vesselName, double vesselLength, double oilCapacity) {
        super(vesselName, "Tanker", vesselLength);
        this.oilCapacity = oilCapacity;
    }

    @Override
    public String toString(){
        return super.toString() + "\nOil Capacity: " + oilCapacity + " barrels";
    }

    @Override
    public void navigateTo(String coordinates) {
        System.out.println("Tanker navigates to " + coordinates);

    }

    @Override
    public double getSpeed() {
        return 15;
    }

    @Override
    public int getCrewSize() {
        return 30;
    }
}
