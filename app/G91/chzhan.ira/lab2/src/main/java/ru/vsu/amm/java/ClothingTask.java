package ru.vsu.amm.java;
import ru.vsu.amm.java.enums.Size;
import ru.vsu.amm.java.interfaces.Wearable;
import ru.vsu.amm.java.classes.Dress;
import ru.vsu.amm.java.classes.Pants;
import ru.vsu.amm.java.classes.Shirt;
import java.util.ArrayList;
import java.util.List;

public class ClothingTask {
    public static void main(String[] args) {
        // Создание объектов одежды
        Shirt shirt = new Shirt("White T-shirt", "Basic cotton T-shirt", 10.0, Size.M);
        Pants pants = new Pants("Black Jeans", "Denim pants", 20.0, Size.L);
        Dress dress = new Dress("Red Dress", "Elegant evening dress", 50.0, Dress.DressLength.LONG);

        // Создание списка одежды
        List<Wearable> clothes = new ArrayList<>();
        clothes.add(shirt);
        clothes.add(pants);
        clothes.add(dress);

        for (Wearable clothing : clothes) {
            System.out.println(clothing);
        }

        if (clothes.get(0) instanceof Shirt) {
            System.out.println("The first element of list is a shirt");
        }

        Shirt anotherShirt = new Shirt("White T-shirt", "Basic cotton T-shirt", 10.0, Size.M);
        if (shirt.equals(anotherShirt)) {
            System.out.println("The shirts are the same!");
        }
        else {
            System.out.println("The shirts are different!");
        }
    }
}

