package ru.vsu.amm.java;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class BuildingTools {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Tool screwdriverScrewingInstrument = new ScrewingTool(
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

        Tool anotherScrewingInstrument = new ScrewingTool(
                "Шуруповёрт",
                10.5,
                22.2,
                "Bosch",
                false,
                "электрический"
        );
        anotherScrewingInstrument.prepare();

        Tool sameScrewingInstrument = new ScrewingTool(
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


        Tool jackhammerHammerInstrument = new HammerTool(
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

    static void defineClass(Tool satellite) {
        if (satellite instanceof ScrewingTool) {
            System.out.println(satellite.getName() + " завинчивающий инструмент");
        } else if (satellite instanceof HammerTool) {
            System.out.println(satellite.getName() + " забивающий инструмент");
        }
    }
}