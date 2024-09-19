package ru.vsu.amm.java;

import java.util.Objects;

public class WeatherSatellite extends SatelliteImpl {

    private Sensor sensorType;

    public WeatherSatellite(String name,
                            double width,
                            double length,
                            boolean isWorking,
                            Sensor sensorType) {
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

    @Override
    public boolean equals(Object obj) {
        WeatherSatellite that = (WeatherSatellite) obj;
        return sensorType == that.sensorType && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getWidth(), getLength(), sensorType);
    }
}
