package ru.vsu.amm.java;

/* Описать иерархию классов для комнатных растений, которая должна содержать:
*
* - интерфейс
* - абстрактный класс
* - несколько наследников одного класса (или реализаций интерфейса)
* - переопределение метода toString
* - переопределение методов equals и hashCode
* - переопределение публичного метода класса-предка
* - использование конструктора с параметрами класса-предка
* - instanceOf
*
* В коде программы продемонстрировать использование принципов ООП на основе созданной иерархии классов.
*/

public class Main {

    public static void encapsulationTest(HousePlant hplant) {
        System.out.println("name: \t" + hplant.getName());
        System.out.println("height: \t" + hplant.getHeight());
    }

    public static void polymorphismTest() {
        HousePlant polymorph = new Orchid("test", 0, 0, true, "test");
        System.out.println(polymorph);
        System.out.println("===================\n");
        polymorph = new Ficus("test", 0, 0, false, 0);
        System.out.println(polymorph);
    }

    public static void instanceOfTest(HousePlant hplant) {
        if (hplant instanceof Orchid) {
            System.out.println("it's an orchid\n");
        }
        else if (hplant instanceof Ficus) {
            System.out.println("it's a ficus\n");
        }
        else {
            System.out.println("it's an unknown house plant\n");
        }
    }

    public static void main(String[] args) {
        Orchid orchid = new Orchid("phalaenopsis", 10, 60, true, "red");
        Ficus ficus = new Ficus("fantasy", 45, 200, false, 6);
        ficus.grow();
        orchid.bloom();
        ficus.bloom();
        System.out.println(orchid);
        System.out.println("====================\n");
        System.out.println(ficus);
        System.out.println("====================\n");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=\n");
        instanceOfTest(orchid);
        System.out.println("=-=-=-=-=-=-=-=-=-=-=\n");
        instanceOfTest(ficus);
        System.out.println("=-=-=-=-=-=-=-=-=-=-=\n");
        encapsulationTest(orchid);
        System.out.println("=-=-=-=-=-=-=-=-=-=-=\n");
        encapsulationTest(ficus);
        System.out.println("=-=-=-=-=-=-=-=-=-=-=\n");
        polymorphismTest();

    }

}
