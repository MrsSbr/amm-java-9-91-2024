package ru.vsu.amm.java;

import java.util.Objects;

public abstract class CatBreedImpl implements CatBreed {
    private String breedName;
    private String description;
    private double averageSize;

    public CatBreedImpl(String breedName, String description, double averageSize) {
        this.breedName = breedName;
        this.description = description;
        this.averageSize = averageSize;
    }

    @Override
    public String getBreedName() {
        return breedName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public double getAverageSize() {
        return averageSize;
    }

    @Override
    public String toString() {
        return "Breed: " + breedName + ", Description: " + description + ", Average Size: " + averageSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CatBreedImpl that = (CatBreedImpl) obj;
        return Double.compare(that.averageSize, averageSize) == 0 &&
                breedName.equals(that.breedName) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(breedName, description, averageSize);
    }

    public void displayInfo() {
        System.out.println(toString());
    }

    public String compareSize(CatBreedImpl other) {
        if (this.averageSize > other.averageSize) {
            return this.breedName + " is generally larger than " + other.breedName;
        } else if (this.averageSize < other.averageSize) {
            return this.breedName + " is generally smaller than " + other.breedName;
        } else {
            return this.breedName + " and " + other.breedName + " are of similar size.";
        }
    }
}

