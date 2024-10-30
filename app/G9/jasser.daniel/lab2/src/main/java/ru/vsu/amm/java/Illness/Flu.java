package ru.vsu.amm.java.Illness;

import ru.vsu.amm.java.Person;

import java.util.ArrayList;

public class Flu extends Illness implements Vaccinable {

    public Flu(String name, int usualDamage, ArrayList<String> symptoms) {
        super(name, usualDamage, symptoms);
    }

    @Override
    public void infect(Person p) {
        p.setDisease("Flu");
        if (p.getImmunity() != -1) {
            if (p.getImmunity() < 30 || p.getAge() > 80) {
                p.conditionGettingWorse(usualDamage);
            } else if (p.getImmunity() < 60 || p.getAge() > 50) {
                p.conditionGettingWorse(usualDamage / 2);
            } else {
                p.conditionGettingWorse(1);
            }
        } else {
            System.out.println("Person is dead");
            p.setDisease("Dead");
        }
    }

    @Override
    public void vaccinate() {
        symptoms = new ArrayList<>();
        usualDamage = 0;
    }
}
