package ru.vsu.amm.java.Illness;

import ru.vsu.amm.java.Person;

import java.util.ArrayList;

public class Cancer extends Illness {

    public Cancer(String name, int usualDamage, ArrayList<String> symptoms) {
        super(name, usualDamage, symptoms);
    }

    @Override
    public void infect(Person p) {
        p.setDisease(this.name);
        if (p.getImmunity() != -1) {
            if (p.getAge() < 18 || p.getImmunity() < 99) {
                p.conditionGettingWorse(usualDamage);
            } else {
                p.conditionGettingWorse(usualDamage * 3);
            }
        } else {
            System.out.println("Person is dead");
            p.setDisease("Dead");
        }
    }
}
