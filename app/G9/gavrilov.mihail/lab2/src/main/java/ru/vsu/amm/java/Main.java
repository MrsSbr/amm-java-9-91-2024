package ru.vsu.amm.java;


import ru.vsu.amm.java.Classes.Bear;
import ru.vsu.amm.java.Classes.Beluga;
import ru.vsu.amm.java.Classes.Wolf;
import ru.vsu.amm.java.Enum.Nutrition;

public class Main {
    public static void main(String[] args) {
        Bear bear = new Bear("Миша", 250, Nutrition.PREDATORY, 4, "White");
        Wolf wolf = new Wolf("Олег", 80, Nutrition.PREDATORY, 4, 35);
        Beluga fish = new Beluga("Кирилл", 5, Nutrition.PREDATORY, 0, true);

        bear.animalSay();
        wolf.animalSay();
        fish.animalSay();

        System.out.println(bear.toString());

        if(!(wolf.equals(bear))){
            System.out.println("Они не равны(");
        }

        Wolf wolf2 = new Wolf ("Бобик", 200, Nutrition.PREDATORY, 4, 45);

        if(!wolf.equals(wolf2)){
            System.out.println("Тоже не равны");
        }

        Wolf wolf3 = new Wolf ("Миша", 250, Nutrition.PREDATORY, 4, 35);

        if(!wolf.equals(wolf3)){
            System.out.println("Жи есть!");
        }

        fish.breathe();
    }
}