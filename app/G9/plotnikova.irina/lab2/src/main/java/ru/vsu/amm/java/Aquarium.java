package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;

public class Aquarium {
    private final List<Fish> fishlist = new ArrayList<>();


    public void add(Fish fish) {
        fishlist.add(fish);
    }


    public void showAquarium() {
        if (fishlist.isEmpty()) {
            System.out.println("Аквариум пуст");
            return;
        }

        System.out.println("Рыбки в аквариуме");
        for (Fish fish : fishlist) {
            fish.swim();
            fish.eat();
            System.out.println(fish.getDescription());

            if (fish instanceof Tuna) {
                System.out.println(fish.getName() + " - молниеносный охотник, рассекающий воды океана в поисках добычи!");
            } else if (fish instanceof  Catfish) {
                System.out.println(fish.getName() + " - таинственный ночной житель, скрывающийся в тени и выслеживающий жертву на дне..");
            } else if (fish instanceof Axolotl) {
                System.out.println(fish.getName() + " - мистическое создание, способное отращивать утраченные части тела, словно настоящее чудо природы!");
                ((Axolotl) fish).regenerate();
            }

            System.out.println();
        }
    }
}
