package ru.vsu.amm.java;

import ru.vsu.amm.java.Illness.Cancer;
import ru.vsu.amm.java.Illness.Flu;
import ru.vsu.amm.java.Illness.Illness;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Person person1 = new Person("Alex", "Black", 20, 30);
        Person person2 = new Person("John", "Grinwood", 76, 120);

        ArrayList<String> symptomsFirstFlu = new ArrayList<String>();
        symptomsFirstFlu.add("Cough");
        symptomsFirstFlu.add("Temperature");
        ArrayList<String> symptomsSecondFlu = new ArrayList<String>();
        symptomsSecondFlu.add("Cough");
        ArrayList<String> symptomsFirstCancer = new ArrayList<String>();
        symptomsFirstCancer.add("Cough");
        symptomsFirstCancer.add("Temperature");
        symptomsFirstCancer.add("Enlarged lympth nodes");
        symptomsFirstCancer.add("Difficulty swallowing");
        ArrayList<String> symptomsSecondCancer = new ArrayList<String>();
        symptomsSecondCancer.add("Cough");
        symptomsFirstCancer.add("Temperature");
        symptomsFirstCancer.add("Blood int the stool");

        Illness fluFirst = new Flu("Swine flu", 10, symptomsFirstFlu);
        Illness fluSecond = new Flu("Bird flu", 5, symptomsSecondFlu);
        Illness cancerFirst = new Cancer("Thyroid cancer", 50, symptomsFirstCancer);
        Illness cancerSecond = new Cancer("Bowel cancer", 70, symptomsSecondCancer);

        Illness[] illnessesList = new Illness[]{fluFirst, fluSecond, cancerFirst, cancerSecond};

        System.out.println("Flu: ");
        printIllnessesInfo(new Illness[]{fluFirst, fluSecond});

        System.out.println("Cancer: ");
        printIllnessesInfo(new Illness[]{cancerFirst, cancerSecond});

        System.out.println("Vacinate flu...");
        for (Illness illness : illnessesList) {
            if (illness instanceof Flu) {
                Flu flu = (Flu) illness;
                flu.vaccinate();
            }
        }

        System.out.println("Flu: ");
        printIllnessesInfo(new Illness[]{fluFirst, fluSecond});

        System.out.println("Persons: ");
        printPersonsInfo(new Person[]{person1, person2});

        System.out.println("Flu season: ");
        fluFirst.infect(person1);
        fluSecond.infect(person2);

        System.out.println("Persons: ");
        printPersonsInfo(new Person[]{person1, person2});

        System.out.println("Got cancer: ");
        cancerFirst.infect(person2);
        cancerSecond.infect(person1);

        System.out.println("Persons: ");
        printPersonsInfo(new Person[]{person1, person2});

        System.out.println("Got cancer: ");
        cancerFirst.infect(person2);
        cancerSecond.infect(person1);

        System.out.println("Persons: ");
        printPersonsInfo(new Person[]{person1, person2});
    }

    public static void printIllnessesInfo(Illness[] illness) {
        for (Illness ill : illness) {
            System.out.println(ill);
        }
    }

    public static void printPersonsInfo(Person[] person) {
        for (Person pers : person) {
            System.out.println(pers);
        }
    }
}

