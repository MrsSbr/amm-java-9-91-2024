package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SatelliteController {
    public static void main(String[] args) {
        Satellite weatherSatellite = new WeatherSatellite(
                "Ромашка",
                2.0,
                6.2,
                true,
                "Инфракрасный")
                ;
        weatherSatellite.perform();
        System.out.println();

        Satellite anotherWeatherSatellite = new WeatherSatellite(
                "Подсолнух",
                1.5,
                3.8,
                false,
                "Оптический");
        anotherWeatherSatellite.on();
        System.out.println();

        Satellite militarySatellite = new MilitarySatellite(
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
        instanceOfDemonstration();
    }

    static void instanceOfDemonstration() {
        List<Satellite> satellites = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int randomInt = new Random().nextInt(2);
            if (randomInt == 0) {
                satellites.add(new WeatherSatellite());
            } else {
                satellites.add(new MilitarySatellite());
            }
        }

        if (satellites.get(0) instanceof WeatherSatellite) {
            System.out.println("Первый спутник в списке - метеорологический");
        } else {
            System.out.println("Первый спутник в списке - военный");
        }

        if (satellites.get(1) instanceof WeatherSatellite) {
            System.out.println("Второй спутник в списке - метеорологический");
        } else {
            System.out.println("Второй спутник в списке - военный");
        }
    }
}