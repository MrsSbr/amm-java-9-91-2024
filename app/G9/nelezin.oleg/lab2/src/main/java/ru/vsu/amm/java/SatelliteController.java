package ru.vsu.amm.java;

public class SatelliteController {
    
    public static void main(String[] args) {
        SatelliteImpl weatherSatellite = new WeatherSatellite(
                "Ромашка",
                2.0,
                6.2,
                true,
                Sensor.INFRARED)
                ;
        weatherSatellite.perform();
        System.out.println();

        SatelliteImpl anotherWeatherSatellite = new WeatherSatellite(
                "Подсолнух",
                1.5,
                3.8,
                false,
                Sensor.OPTIC);
        SatelliteImpl similarWeatherSatellite = new WeatherSatellite(
                "Подсолнух",
                1.5,
                3.8,
                false,
                Sensor.OPTIC
        );
        System.out.println(anotherWeatherSatellite.equals(similarWeatherSatellite));
        anotherWeatherSatellite.on();
        System.out.println();

        SatelliteImpl militarySatellite = new MilitarySatellite(
                "Дазл",
                4,
                7.7,
                true,
                "Ядерное оружие");
        System.out.println(militarySatellite);
        System.out.println();

        System.out.println("Ромашка equals Подсолнух: " + weatherSatellite.equals(anotherWeatherSatellite));
        System.out.println("Ромашка hashCode: " + weatherSatellite.hashCode());
        System.out.println("Подсолнух hashCode: " + anotherWeatherSatellite.hashCode());
        System.out.println("Демонстрация instanceOf->");
        defineClass(militarySatellite);
        defineClass(weatherSatellite);
    }

    static void defineClass(SatelliteImpl satellite) {
        if (satellite instanceof WeatherSatellite) {
            System.out.println(satellite.getName() + " метеорологический спутник");
        } else if (satellite instanceof MilitarySatellite) {
            System.out.println(satellite.getName() + " военный спутник");

        }
    }
}