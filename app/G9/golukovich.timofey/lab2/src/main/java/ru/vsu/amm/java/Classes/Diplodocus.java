package ru.vsu.amm.java.Classes;

public class Diplodocus extends Dinosaur {
    protected int height;

    public Diplodocus(int age, String name, String habitat, int height) {
        super(age, name, habitat);
        this.height = height;
    }

    @Override
    public void growUp() {
        age += 2;
        height += 1;
    }

    @Override
    public void saySomething() {
        System.out.println("Мой рост - " + height + ".");
    }

    @Override
    public String toString() {
        return "Диплодок - Возраст: " + age + ", Имя: " + name
                + ", Место обитания: " + habitat + ", Рост: " + height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Diplodocus)) {
            return false;
        }
        Diplodocus diplodocus = (Diplodocus) o;
        boolean isNumbersEquals = age == diplodocus.age
                && height == diplodocus.height;
        boolean isNamesEquals = (name == null && diplodocus.name == null
                || name != null && name.equals(diplodocus.name));
        boolean isHabitatsEquals = (habitat == null && diplodocus.habitat == null
                || habitat != null && habitat.equals(diplodocus.habitat));
        return isNumbersEquals && isNamesEquals && isHabitatsEquals;
    }

    @Override
    public final int hashCode() {
        int result = age + height;
        for (var c : name.toCharArray()) {
            result += c % 24;
        }
        for (var c : habitat.toCharArray()) {
            result += c % 42;
        }
        return result;
    }
}
