package ru.vsu.amm.java.classes;

public abstract class Dinosaur {
    protected int age;
    protected String name;
    protected String habitat;

    public Dinosaur(int age, String name, String habitat) {
        this.age = age;
        this.name = name;
        this.habitat = habitat;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getHabitat() {
        return habitat;
    }

    public void growUp() {
        age += 1;
    }

    public void eat() {
        System.out.println(name + " поел!");
    }
    public void saySomething() {
        System.out.println(this);
    }

    public String toString() {
        return "I'm a dinosaur!!!";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dinosaur dino = (Dinosaur) o;
        boolean isNamesEquals = (name == null && dino.name == null
                || name != null && name.equals(dino.name));
        boolean isHabitatsEquals = (habitat == null && dino.habitat == null
                || habitat != null && habitat.equals(dino.habitat));
        return age == dino.age && isNamesEquals && isHabitatsEquals;
    }

    @Override
    public int hashCode() {
        int result = age;
        for (var c : name.toCharArray()) {
            result += c % 24;
        }
        for (var c : habitat.toCharArray()) {
            result += c % 42;
        }
        return result;
    }
}
