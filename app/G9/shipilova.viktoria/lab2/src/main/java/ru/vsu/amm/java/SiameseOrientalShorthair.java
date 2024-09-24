package ru.vsu.amm.java;

import java.util.Objects;

public class SiameseOrientalShorthair extends CatBreedImpl {
    public SiameseOrientalShorthair(String breedName, String description) {
        super(breedName, description, 3.5);
    }

    @Override
    public void displayInfo() {
        System.out.println("Siamese-Oriental Shorthair Cat: " + toString());
    }
}
