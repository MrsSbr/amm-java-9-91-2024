package ru.vsu.amm.java;

import ru.vsu.amm.java.interfaces.Swimmable;
import ru.vsu.amm.java.interfaces.Walkable;

@ExtractInterface
public class Duck implements Swimmable, Walkable {
    @Override
    public void swim() {
        System.out.println("Swimming Duck");
    }

    @Override
    public void walk() {
        System.out.println("Walking Duck");
    }
}
