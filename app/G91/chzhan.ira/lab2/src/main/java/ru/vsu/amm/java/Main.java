package ru.vsu.amm.java;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создание объектов одежды
        Shirt shirt = new Shirt("White T-shirt", "Basic cotton T-shirt", 10.0, "M");
        Pants pants = new Pants("Black Jeans", "Denim pants", 20.0, "32");
        Dress dress = new Dress("Red Dress", "Elegant evening dress", 50.0, "Long");

        // Создание списка одежды
        List<Clothing> clothes = new ArrayList<>();
        clothes.add(shirt);
        clothes.add(pants);
        clothes.add(dress);

        for (Clothing clothing : clothes) {
            System.out.println(clothing);
        }

        if (clothes.get(0) instanceof Shirt) {
            System.out.println("Первый элемент списка - рубашка");
        }

        Shirt anotherShirt = new Shirt("White T-shirt", "Basic cotton T-shirt", 10.0, "M");
        if (shirt.equals(anotherShirt)) {
            System.out.println("Рубашки одинаковые!");
        }
        else {
            System.out.println("Рубашки разные!");
        }
    }
}

