package ru.vsu.amm.java;

import static ru.vsu.amm.java.JsonDeserializer.deserialize;
import static ru.vsu.amm.java.JsonSerializer.serialize;

public class Main {
    public static void main(String[] args) throws Exception {
        Cat cat = new Cat("Vasya", 10, Gender.MALE, null, false);
        String json = serialize(cat);
        System.out.println("Serialized JSON:");
        System.out.println(json);

        Cat newCat = deserialize(json, Cat.class);
        System.out.println("Deserialized Object:");
        System.out.println(newCat);
    }
}