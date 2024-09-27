package ru.vsu.amm.java.classes;

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
        if (!(o instanceof Diplodocus diplodocus)) {
            return false;
        }
        return super.equals(o) && height == diplodocus.height;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + height;
    }
}
