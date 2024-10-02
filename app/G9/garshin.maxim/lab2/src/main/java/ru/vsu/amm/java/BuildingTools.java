package ru.vsu.amm.java;

public class BuildingTools {
    public static void main(String[] args) {
        InstrumentImpl screwdriverScrewingInstrument = new ScrewingInstrument(
                "Отвёртка",
                5.1,
                14.6,
                "Makita",
                true,
                "механическая"
        );
        screwdriverScrewingInstrument.use();
        System.out.println(screwdriverScrewingInstrument);
        screwdriverScrewingInstrument.prepare();
        System.out.println();

        InstrumentImpl anotherScrewingInstrument = new ScrewingInstrument(
                "Шуруповёрт",
                10.5,
                22.2,
                "Bosch",
                false,
                "электрический"
        );
        anotherScrewingInstrument.prepare();

        InstrumentImpl sameScrewingInstrument = new ScrewingInstrument(
                "Шуруповёрт",
                10.5,
                22.2,
                "Bosch",
                false,
                "электрический"
        );

        System.out.println(anotherScrewingInstrument.equals(sameScrewingInstrument));
        anotherScrewingInstrument.use();
        System.out.println();


        InstrumentImpl jackhammerHammerInstrument = new HammerInstrument(
                "Отбойный молоток",
                25,
                120.5,
                "Энкор",
                true,
                400
        );
        jackhammerHammerInstrument.use();
        System.out.println();

        System.out.println("Отвёртка equals шуруповёрт: " + screwdriverScrewingInstrument.equals(anotherScrewingInstrument));
        System.out.println("hashCode отвёртки: " + screwdriverScrewingInstrument.hashCode());
        System.out.println("hashCode шуруповёрта: " + anotherScrewingInstrument.hashCode());
        System.out.println("Работа instanceOf:");
        defineClass(screwdriverScrewingInstrument);
        defineClass(jackhammerHammerInstrument);
    }

    static void defineClass(InstrumentImpl satellite) {
        if (satellite instanceof ScrewingInstrument) {
            System.out.println(satellite.getName() + " завинчивающий инструмент");
        } else if (satellite instanceof HammerInstrument) {
            System.out.println(satellite.getName() + " забивающий инструмент");
        }
    }
}