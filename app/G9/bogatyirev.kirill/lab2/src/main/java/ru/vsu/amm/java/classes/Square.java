package ru.vsu.amm.java.classes;

public class Square extends Figure{
    protected int sideSize;

    public Square(String name, String color/*, double diagonal*/, int sideSize){
        super(name, color/*, diagonal*/);
        this.sideSize = sideSize;
    }

    public double findPerimeter(){
        return sideSize * 4;
    }

    public void draw(){
        System.out.println("Имя фигуры: " + name);
        System.out.println("Цвет фигуры: " + color);
        // System.out.println("Размер диагонали фигуры: "/* + diagonal*/);

        System.out.println("Размер стороны: " + sideSize);
        System.out.println("--------------");

    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;

        if (!(obj instanceof Square square)) {
            return false;
        }
        return super.equals(obj) && sideSize == square.sideSize;
    }

}