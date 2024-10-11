package ru.vsu.amm.java.catbreed;

public class SemiLonghair extends CatBreed implements Groomable {
    public SemiLonghair(String breedName, String description) {
        super(breedName, description, 6.0);
    }

    public void play() {
        System.out.println(getBreedName() + " loves to play.");
    }

    @Override
    public void displayInfo() {
        System.out.println("Semi-longhair Cat: " + toString());
    }

    @Override
    public void groom() {
        System.out.println(getBreedName() + " sometimes requires grooming.");
    }
}