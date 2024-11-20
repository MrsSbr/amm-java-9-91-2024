package ru.vsu.amm.java;

public class CatWinner {
    private String name;
    private Breed breed;
    private Gender gender;

    public CatWinner(String name, Breed breed, Gender gender){
        this.name = name;
        this.breed = breed;
        this.gender = gender;
    }

    public String getName(){
        return name;
    }

    public Breed getBreed(){
        return breed;
    }

    public Gender getGender(){
        return gender;
    }
}
