package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;

public class Zoo {
    public static void main(String[] args) {

        List<ZooAnimal> animals = new ArrayList<ZooAnimal>();

        animals.add(new Parrot("Polly", 2, "Hello"));
        animals.add(new Parrot("Rio", 1, "Hi"));
        animals.add(new Lion("Simba", 5, 150));
        animals.add(new Lion("Mufasa", 10, 175));

        for (ZooAnimal animal : animals) {
            System.out.println(animal);
        }

        System.out.println("----------------------------------------");
        for (ZooAnimal animal : animals) {
            if (animal instanceof Parrot) {
                System.out.println(animal.getName() + " is Parrot");
            }
            animal.move();
            animal.makeSound();
            System.out.println("-----------------------");
        }

        if(animals.get(0).equals(animals.get(1))) {
            System.out.println(animals.get(0) + " equals " + animals.get(1));
        } else {
            System.out.println(animals.get(0) + " not equals " + animals.get(1));
        }
    }
}