package ru.vsu.amm.java;

public class Cat {
    String name;
    int age;
    Gender gender;
    String breed;
    boolean defertilized;

    public Cat() {
    }

    public Cat(String name, int age, Gender gender, String breed, boolean defertilized) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.breed = breed;
        this.defertilized = defertilized;
    }

    @Override
    public String toString() {
        return "class Cat {\n" +
                "  name = \"" + name + "\";\n" +
                "  age = " + age + ";\n" +
                "  gender = " + gender + ";\n" +
                "  breed = \"" + breed + "\";\n" +
                "  defertilized  = " + defertilized + ";\n" +
                '}';
    }
}
