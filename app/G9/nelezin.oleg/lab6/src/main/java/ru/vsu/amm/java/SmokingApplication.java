package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.Barman;
import ru.vsu.amm.java.classes.Smoker;
import ru.vsu.amm.java.classes.Table;
import ru.vsu.amm.java.enums.Component;

public class SmokingApplication {

    public static void main(String[] args) {
        Table table = new Table();

        Thread firstSmoker = new Thread(
                new Smoker("Степан", Component.PAPER.getName(), table)
        );

        Thread secondSmoker = new Thread(
                new Smoker("Алексей", Component.MATCHES.getName(), table)
        );

        Thread thirdSmoker = new Thread(
                new Smoker("Руслан", Component.TOBACCO.getName(), table)
        );

        Thread barman = new Thread(new Barman(table));

        firstSmoker.start();
        secondSmoker.start();
        thirdSmoker.start();
        barman.start();
    }
}