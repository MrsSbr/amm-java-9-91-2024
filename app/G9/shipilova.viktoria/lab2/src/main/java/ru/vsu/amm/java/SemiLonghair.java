package ru.vsu.amm.java;

import java.util.Objects;

public class SemiLonghair extends CatBreedImpl {
    public SemiLonghair(String breedName, String description) {
        super(breedName, description, 6.0);
    }

    @Override
    public void displayInfo() {
        System.out.println("Semi-longhair Cat: " + toString());
    }
}
