package ru.vsu.amm.java;

import java.util.Objects;

public class Shorthair extends CatBreedImpl {
    public Shorthair(String breedName, String description) {
        super(breedName, description, 5.0);
    }

    @Override
    public void displayInfo() {
        System.out.println("Shorthair Cat: " + toString());
    }
}
