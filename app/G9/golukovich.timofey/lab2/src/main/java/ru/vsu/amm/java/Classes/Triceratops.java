package ru.vsu.amm.java.Classes;

public class Triceratops extends Dinosaur {
    protected int hornsLength;

    public Triceratops(int age, String name, String habitat, int hornsLength) {
        super(age, name, habitat);
        this.hornsLength = hornsLength;
    }

    @Override
    public void growUp() {
        age += 2;
        hornsLength += 1;
    }

    @Override
    public void saySomething() {
        System.out.println("Длинна моих рогов - " + hornsLength + ".");
    }

    @Override
    public String toString() {
        return "Трицератопс - Возраст: " + age + ", Имя: " + name
                + ", Место обитания: " + habitat + ", Длинна рогов: " + hornsLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Triceratops)) {
            return false;
        }
        Triceratops triceratops = (Triceratops) o;
        boolean isNumbersEquals = age == triceratops.age
                && hornsLength == triceratops.hornsLength;
        boolean isNamesEquals = (name == null && triceratops.name == null
                || name != null && name.equals(triceratops.name));
        boolean isHabitatsEquals = (habitat == null && triceratops.habitat == null
                || habitat != null && habitat.equals(triceratops.habitat));
        return isNumbersEquals && isNamesEquals && isHabitatsEquals;
    }

    @Override
    public final int hashCode() {
        int result = age + hornsLength;
        for (var c : name.toCharArray()) {
            result += c % 24;
        }
        for (var c : habitat.toCharArray()) {
            result += c % 42;
        }
        return result;
    }
}
