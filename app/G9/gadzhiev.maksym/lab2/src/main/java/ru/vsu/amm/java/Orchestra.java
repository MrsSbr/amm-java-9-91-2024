package ru.vsu.amm.java;

public class Orchestra {
    public static void main(String[] args) {

        InstrumentImpl stringInstrument = new StringInstrument(
                "Скрипка",
                 400,
                 "Дерево",
                 4
        );

        InstrumentImpl windInstrumentOne = new WindInstrument(
                "Кларнет",
                850,
                "Дерево",
                60
        );

        InstrumentImpl windInstrumentTwo = new WindInstrument(
                "Саксофон",
                3000,
                "Металл",
                70
        );

        InstrumentImpl windInstrumentThree = new WindInstrument(
                "Кларнет",
                850,
                "Дерево",
                60
        );

        windInstrumentOne.play();
        windInstrumentTwo.play();
        stringInstrument.play();
        System.out.println("Кларнет equals кларнет " + windInstrumentOne.equals(windInstrumentThree));
        System.out.println("Кларнет equals саксофон " + windInstrumentOne.equals(windInstrumentTwo));
        System.out.println("Саксофон hashCode " + windInstrumentTwo.hashCode());
        System.out.println("Скрипка hashCode " + stringInstrument.hashCode());

        if (stringInstrument instanceof StringInstrument) {
            System.out.println(stringInstrument.getName() + " это струнный инструмент!");
        }  else if (stringInstrument instanceof WindInstrument) {
            System.out.println(stringInstrument.getName() + " это духовой инструмент!");
        }
    }
}