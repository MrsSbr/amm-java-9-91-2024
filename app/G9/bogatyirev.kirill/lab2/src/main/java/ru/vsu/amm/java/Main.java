package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.Square;
import ru.vsu.amm.java.classes.Circle;
import ru.vsu.amm.java.classes.Triangle;

public class Main {
    public static void main(String[] args) {
        Square square1 = new Square("Квадрат 1", "Синий", 5);
        Square square2 = new Square("Квадрат 2", "Красный", 6);

        square1.draw();
        square2.draw();

        if (square1.equals(square2)){
            System.out.println("Квадраты равны");
        } else {
            System.out.println("Квадраты не равны");
        }

        double perimeterSquare = square1.findPerimeter();
        System.out.println("Периметр " + square1.getName() + " = " + perimeterSquare);

        System.out.println("\n---------Следующая фигура-----------\n");


        Circle circle1 = new Circle("Круг 1", "Зеленый", 6);
        circle1.draw();

        double perimeterCircle = circle1.findPerimeter();
        System.out.println("Периметр " + circle1.getName() + " = " + perimeterCircle);

        double radiusCircle = circle1.findRadius();
        System.out.println("Радиус " + circle1.getName() + " = " + radiusCircle);

        int circleHash = circle1.hashCode();
        System.out.println("Хэш круга " + circleHash);

        String stringCircle = circle1.toString();
        System.out.println("Круг в строку " + stringCircle);

        System.out.println("\n---------Следующая фигура-----------\n");


        Triangle triangle1 = new Triangle("Треугольник 1", "Желтый", 1, 2, 1);
        triangle1.draw();

        double perimeterTriangle = triangle1.findPerimeter();
        System.out.println("Периметр " + triangle1.getName() + " = " + perimeterTriangle);

        double medianTriangle = triangle1.findMedian();
        System.out.println("Медиана " + triangle1.getName() + " = " + medianTriangle);

        Triangle triangle2 = new Triangle("Треугольник 2", "Желтый", 1, 2, 1);
        triangle2.draw();

        if (triangle1.equals(triangle2)){
            System.out.println("Треугольники равны");
        } else {
            System.out.println("Треугольники не равны");
        }
    }
}