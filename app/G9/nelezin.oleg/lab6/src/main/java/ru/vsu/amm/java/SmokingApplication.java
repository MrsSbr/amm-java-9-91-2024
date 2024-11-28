package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.Barman;
import ru.vsu.amm.java.classes.Smoker;
import ru.vsu.amm.java.classes.Table;
import ru.vsu.amm.java.enums.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SmokingApplication {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Table table = new Table();

        execute(executorService, table);

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(20, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            System.out.println(e.getMessage());
        }
    }

    public static void execute(ExecutorService executorService, Table table) {
        executorService.submit(new Smoker("Степан", Component.PAPER.getName(), table));

        executorService.submit(new Smoker("Алексей", Component.MATCHES.getName(), table));

        executorService.submit(new Smoker("Руслан", Component.TOBACCO.getName(), table));

        executorService.submit(new Barman(table));
    }
}