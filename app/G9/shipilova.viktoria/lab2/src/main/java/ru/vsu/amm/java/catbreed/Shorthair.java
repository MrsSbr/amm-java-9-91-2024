package ru.vsu.amm.java.catbreed;

public class Shorthair extends CatBreed {
    public Shorthair(String breedName, String description) {
        super(breedName, description, 5.0);
    }

    public void hunt() {
        System.out.println(getBreedName() + " is a great hunter.");
    }

    @Override
    public void displayInfo() {
        System.out.println("Shorthair Cat: " + toString());
    }
}