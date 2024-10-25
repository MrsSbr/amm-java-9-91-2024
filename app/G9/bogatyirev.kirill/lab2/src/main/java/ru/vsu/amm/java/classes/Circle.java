package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.interfaces.Circlable;

public class Circle extends Figure implements Circlable{
    private final double PI = 3.1415;
    private double diagonal;

    public Circle(String name, String color, double diagonal){
        super(name, color);
        this.diagonal = diagonal;
    }

    public double findPerimeter(){
        return 2*PI*findRadius();
    }

    public void draw(){
        System.out.println("Имя фигуры: " + getName());
        System.out.println("Цвет фигуры: " + getColor());

        System.out.println("Размер диагонали фигуры: " + diagonal);
        System.out.println("Число Пи, определенное для круга: " + PI);
        System.out.println("--------------");
    }

    public double findRadius(){
        return diagonal / 2;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Circle circle)) {
            return false;
        }

        return super.equals(obj) && diagonal == circle.diagonal;
    }
}
