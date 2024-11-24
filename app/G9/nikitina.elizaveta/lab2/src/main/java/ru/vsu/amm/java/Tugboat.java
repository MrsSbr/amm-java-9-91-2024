package ru.vsu.amm.java;

class Tugboat extends WaterVessel implements Navigable {
    private int horsePower;
    public Tugboat(String vesselName, double vesselLength, int horsePower) {
        super(vesselName, "Tugboat", vesselLength);
        this.horsePower = horsePower;
    }

    @Override
    public String toString(){
        return super.toString() + "\nHorsePower: " + horsePower;
    }

    @Override
    public void navigateTo(String coordinates) {
        System.out.println("Tugboat navigates to " + coordinates);
    }

    @Override
    public double getSpeed() {
        return 10;
    }

    @Override
    public int getCrewSize() {
        return 5;
    }
}
