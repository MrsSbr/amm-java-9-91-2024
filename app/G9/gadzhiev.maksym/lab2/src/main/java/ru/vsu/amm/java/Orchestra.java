package ru.vsu.amm.java;

public class Orchestra {
    public static void main(String[] args) {

        Instrument stringInstrument = new StringInstrument(
                "Скрипка",
                 400,
                 Material.TREE,
                 4
        );

        stringInstrument.play();
        stringInstrument.tune();
        System.out.println(stringInstrument.toString());

        Instrument windInstrumentOne = new WindInstrument(
                "Кларнет",
                850,
                Material.TREE,
                60
        );

        Instrument windInstrumentTwo = new WindInstrument(
                "Саксофон",
                3000,
                Material.METAL,
                70
        );

        Instrument windInstrumentThree = new WindInstrument(
                "Кларнет",
                850,
                Material.TREE,
                60
        );
        windInstrumentOne.tune();
        windInstrumentOne.play();
        windInstrumentTwo.play();

        System.out.println();
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