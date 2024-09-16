package ru.vsu.amm.java;

public class WeatherSatellite extends Satellite {

    private String sensorType;

    public WeatherSatellite(String name,
                            double width,
                            double length,
                            boolean isWorking,
                            String sensorType) {
        super(name, width, length, isWorking);
        this.sensorType = sensorType;
    }

    public WeatherSatellite() {
        super();
    }

    @Override
    public void perform() {
        System.out.println(getName() + " проводит настройку сенсора, бип-бип!\n" +
                "Тип сенсора: " + sensorType);
    }

    @Override
    public String toString() {
        return "Метеорологический спутник\n" +
                super.toString() +
                "Тип сенсора: " + sensorType;
    }
}
