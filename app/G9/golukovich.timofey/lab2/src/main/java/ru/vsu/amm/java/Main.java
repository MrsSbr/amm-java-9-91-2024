package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.Dinosaur;
import ru.vsu.amm.java.classes.Diplodocus;
import ru.vsu.amm.java.classes.TRex;
import ru.vsu.amm.java.classes.Triceratops;

public class Main {
    public static void main(String[] args) {
        TRex tRex = new TRex(13, "Tyrannosaur Rex", "Earth", "RRROOOAAAAAARRRRRRR");
        var triceratops = new Triceratops(20, "Triceratops Trip", "Earth", 40);
        Dinosaur diplodocus = new Diplodocus(18, "Diplodocus Dip", "Earth", 30);

        System.out.println(tRex);
        tRex.eat();
        tRex.saySomething();
        System.out.println();

        System.out.println(triceratops);
        triceratops.eat();
        triceratops.saySomething();
        System.out.println();

        System.out.println(diplodocus);
        diplodocus.eat();
        diplodocus.saySomething();
        System.out.println();

        if (tRex.equals(triceratops) || triceratops.equals(diplodocus) || diplodocus.equals(tRex)) {
            System.out.println("Ошибка, невозможно такое.\n");
        } else {
            System.out.println("Никто не равен друг другу.\n");
        }

        Dinosaur dino = new TRex(10, "Tyrannosaur Rex", "Earth", "RRROOOAAAAAARRRRRRR");
        System.out.println("Сравниваем нового тиранозавра со старым.");
        if (dino.equals(tRex)) {
            System.out.println("Что-то пошло не так.");
        } else {
            System.out.println("Тиранозавры не равны.\n");
        }
        dino.growUp();
        System.out.println("Новый тиранозавр подрос.");
        if (dino.equals(tRex)) {
            System.out.println("Другое дело, теперь тиранозавры одинаковы.");
            System.out.println("А хэш-код вот такой получился: " + dino.hashCode() + "\n");
        } else {
            System.out.println("Что-то пошло не так.\n");
        }
        tRex.roar();
    }
}