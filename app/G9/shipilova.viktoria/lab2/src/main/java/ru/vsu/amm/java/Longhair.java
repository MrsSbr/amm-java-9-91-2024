package ru.vsu.amm.java;

import java.util.Objects;

public class Longhair extends CatBreedImpl {
    public Longhair(String breedName, String description) {
        super(breedName, description, 4.5);
    }

    @Override
    public void displayInfo() {
        System.out.println("Longhair Cat: " + toString());
    }
}
