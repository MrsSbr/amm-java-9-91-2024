package ru.vsu.amm.java;

public class Main {
    public static void main(String[] args) {
        Aquarium aquarium = new Aquarium();

        Fish tuna = new Tuna("Тунец", 20.6);
        Fish catfish = new Catfish("Сом", 12.8);
        Fish axolotl = new Axolotl("Аксолотль", 1.25, true);


        aquarium.add(tuna);
        aquarium.add(catfish);
        aquarium.add(axolotl);

        System.out.println("\n=== Работа аквариума рыбок ===");
        aquarium.showAquarium();


        System.out.println("\n=== Сравнение объектов ===");
        Fish axolotl2 = new Axolotl("Аксолотль", 1.25, true);
        System.out.println("axolotl equals axolotl2: " + axolotl.equals(axolotl2));
        System.out.println("axolotl hashCode: " + axolotl.hashCode());
        System.out.println("axolotl2 hashCode: " + axolotl2.hashCode());
    }
}
