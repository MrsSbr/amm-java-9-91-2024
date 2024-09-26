package ru.vsu.amm.java.Classes;

import ru.vsu.amm.java.Interfaces.Roarable;

public class TRex extends Dinosaur implements Roarable {
    protected String roar;

    public TRex(int age, String name, String habitat, String roar) {
        super(age, name, habitat);
        this.roar = roar;
    }

    @Override
    public void growUp() {
        age += 3;
    }

    @Override
    public void saySomething() {
        System.out.println("Меня зовут " + name
                + ", я тиранозавр и умею рычать: " + roar);
    }

    @Override
    public String toString() {
        return "Тиранозавр - Возраст: " + age + ", Имя: " + name
                + ", Место обитания: " + habitat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TRex)) {
            return false;
        }
        TRex tRex = (TRex) o;
        boolean isNamesEquals = (name == null && tRex.name == null
                || name != null && name.equals(tRex.name));
        boolean isHabitatsEquals = (habitat == null && tRex.habitat == null
                || habitat != null && habitat.equals(tRex.habitat));
        boolean isRoarsEquals = (roar == null && tRex.roar == null
                || roar != null && roar.equals(tRex.roar));
        return age == tRex.age && isNamesEquals && isHabitatsEquals
                && isRoarsEquals;
    }

    @Override
    public final int hashCode() {
        int result = age;
        for (var c : name.toCharArray()) {
            result += c % 24;
        }
        for (var c : habitat.toCharArray()) {
            result += c % 42;
        }
        for (var c : roar.toCharArray()) {
            result += c % 33;
        }
        return result;
    }

    @Override
    public void roar() {
        System.out.println(roar);
    }
}
