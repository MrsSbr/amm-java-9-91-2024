package ru.vsu.amm.java.catbreed;

public class Longhair extends CatBreed implements Groomable {
    public Longhair(String breedName, String description) {
        super(breedName, description, 4.5);
    }

    public void shed() {
        System.out.println(getBreedName() + " sheds a lot.");
    }

    @Override
    public void displayInfo() {
        System.out.println("Longhair Cat: " + toString());
    }

    @Override
    public void groom() {
        System.out.println(getBreedName() + " requires regular grooming.");
    }
}