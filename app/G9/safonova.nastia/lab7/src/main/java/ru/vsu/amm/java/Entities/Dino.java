package ru.vsu.amm.java.Entities;

import ru.vsu.amm.java.Enums.Kind;

import java.time.LocalDate;

public class Dino {
    private long idDino;
    private double weight;
    private LocalDate dateOfDeath;
    private LocalDate dateOfBirthDino;
    private Kind kindOfDino;
    private String nameDino;

    public Dino() {}

    public Dino(long idDino, double weight, LocalDate dateOfDeath, LocalDate dateOfBirthDino, Kind kindOfDino, String nameDino) {
        this.idDino = idDino;
        this.weight = weight;
        this.dateOfDeath = dateOfDeath;
        this.dateOfBirthDino = dateOfBirthDino;
        this.kindOfDino = kindOfDino;
        this.nameDino = nameDino;
    }

    public long getIdDino() {
        return idDino;
    }

    public void setIdDino(long idDino) {
        this.idDino = idDino;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public LocalDate getDateOfBirthDino() {
        return dateOfBirthDino;
    }

    public void setDateOfBirthDino(LocalDate dateOfBirthDino) {
        this.dateOfBirthDino = dateOfBirthDino;
    }

    public Kind getKindOfDino() {
        return kindOfDino;
    }

    public void setKindOfDino(Kind kindOfDino) {
        this.kindOfDino = kindOfDino;
    }

    public String getNameDino() {
        return nameDino;
    }

    public void setNameDino(String nameDino) {
        this.nameDino = nameDino;
    }
}