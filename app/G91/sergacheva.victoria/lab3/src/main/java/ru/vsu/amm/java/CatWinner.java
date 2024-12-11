package ru.vsu.amm.java;

import java.util.Objects;

public class CatWinner {
    private String name;
    private Breed breed;
    private Gender gender;

    public CatWinner(String name, Breed breed, Gender gender) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public Breed getBreed() {
        return breed;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, breed, gender);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatWinner catWinner = (CatWinner) o;
        return Objects.equals(name, catWinner.name) &&
                breed == catWinner.breed &&
                gender == catWinner.gender;
    }
}
