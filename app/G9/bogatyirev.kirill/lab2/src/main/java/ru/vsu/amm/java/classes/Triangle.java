package ru.vsu.amm.java.classes;

public class Triangle extends Figure{
    protected double firstSideSize;
    protected double secondSideSize;
    protected double thirdSideSize;

    public Triangle(String name, String color, double firstSideSize, double secondSideSize, double thirdSideSize) {
        super(name, color);
        if (!isValidTriangle(firstSideSize, secondSideSize, thirdSideSize)) {
            throw new IllegalArgumentException("Cannot form a triangle with sides: " +
                    firstSideSize + ", " + secondSideSize + ", " + thirdSideSize);
        }
        this.firstSideSize = firstSideSize;
        this.secondSideSize = secondSideSize;
        this.thirdSideSize = thirdSideSize;
    }

    private boolean isValidTriangle(double a, double b, double c) {
        return a + b >= c && a + c >= b && b + c >= a;
    }

    public double findMedian(){
        return 0.5 * Math.sqrt(2 * (secondSideSize * secondSideSize + thirdSideSize * thirdSideSize)
                - firstSideSize * firstSideSize);
    }

    public double findPerimeter(){
        return firstSideSize + secondSideSize + thirdSideSize;
    }

    public void draw(){
        System.out.println("Имя фигуры: " + name);
        System.out.println("Цвет фигуры: " + color);
        // System.out.println("Размер диагонали фигуры: "/* + diagonal*/);

        System.out.println("Размер сторон треугольника: ");
        System.out.println(firstSideSize);
        System.out.println(secondSideSize);
        System.out.println(thirdSideSize);

        System.out.println("--------------");
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;

        if (!(obj instanceof Triangle triangle)) {
            return false;
        }
        return super.equals(obj) &&
                firstSideSize == triangle.firstSideSize &&
                secondSideSize == triangle.secondSideSize &&
                thirdSideSize == triangle.thirdSideSize;
    }
}
