package ru.vsu.amm.java;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Main {
    public static void main(String[] args) {

        List<ConstructionMachineImpl> machines = Arrays.asList(
                new Digger("Digger1", 10, 10000),
                new ConcreteMixer("ConcreteMixer1", 20, 400),
                new Digger("Digger2", 20, 12000),
                new ConcreteMixer("ConcreteMixer2", 20, 500),
                new Digger("Digger3", 30, 15000),
                new ConcreteMixer("ConcreteMixer3", 20, 600)
        );

        for (var machine : machines) {
            System.out.println(machine.toString());

            machine.startEngine();
            machine.working();
            machine.stopEngine();
            System.out.println( machine.hashCode());
            System.out.println();
        }

        Random random = new Random();
        ConstructionMachineImpl machine = machines.get(random.nextInt(4));

        if (machine instanceof Digger) {
            System.out.println("\nITS DIGGER\n");
        } else {
            System.out.println("\nITS MIXER\n");
        }

    }
}