package ru.vsu.amm.java;

import java.util.Objects;

public abstract class Bee implements ActionsBee {
    private BeeType type;
    private int age;

    public Bee(BeeType type, int age) {
        this.type = type;
        this.age = age;
    }

    public BeeType getType() {
        return type;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Type: " + type + ", Age: " + age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Bee other = (Bee) obj;
        return age == other.age && type.equals(other.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, age);
    }
}
