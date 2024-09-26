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
        return super.equals(o) && hornsLength == triceratops.hornsLength;
    }

    @Override
    public final int hashCode() {
        return super.hashCode() + hornsLength;
    }
}
