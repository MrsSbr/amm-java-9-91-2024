package ru.vsu.amm.java;
import java.util.Objects;

public abstract class FarmAnimal implements Farmable {
    private final String name;
    private final AnimalType type;

    public FarmAnimal(String name, AnimalType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public AnimalType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Animal: " + name + ", type: " + type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FarmAnimal)) {
            return false; //является ли obj экземпляром farmanimal
        }
        FarmAnimal other = (FarmAnimal) obj; //приведение obj к типу farmaniaml
        return name.equals(other.name) && type.equals(other.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}