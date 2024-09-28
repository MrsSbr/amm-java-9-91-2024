package ru.vsu.amm.java.catbreed;

public class SiameseOrientalShorthair extends CatBreed {
    public SiameseOrientalShorthair(String breedName, String description) {
        super(breedName, description, 3.5);
    }

    public void vocalize() {
        System.out.println(getBreedName() + " is very vocal.");
    }

    @Override
    public void displayInfo() {
        System.out.println("Siamese-Oriental Shorthair Cat: " + toString());
    }
}
