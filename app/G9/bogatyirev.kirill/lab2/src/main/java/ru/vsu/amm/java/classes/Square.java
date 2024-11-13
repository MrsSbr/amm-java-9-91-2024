package ru.vsu.amm.java.classes;

public class Square extends Figure{
    private int sideSize;

    public Square(String name, String color, int sideSize){
        super(name, color);
        this.sideSize = sideSize;
    }

    public double findPerimeter(){
        return sideSize * 4;
    }

    public void draw(){
        System.out.println("Имя фигуры: " + getName());
        System.out.println("Цвет фигуры: " + getColor());

        System.out.println("Размер стороны: " + sideSize);
        System.out.println("--------------");
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Square square)) {
            return false;
        }

        return super.equals(obj) && sideSize == square.sideSize;
    }
}