package ru.vsu.amm.java.classes;

public abstract class Figure{
    protected String name;
    protected String color;

    public Figure(String name, String color){
        this.name = name;
        this.color = color;
    }

    public String getName(){
        return name;
    }

    public String getColor(){
        return color;
    }


    abstract public double findPerimeter();

    abstract public void draw();

    @Override
    public int hashCode() {
        int result = -1;
        for(char c : name.toCharArray())
            result += c % 8;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;


        Figure f = (Figure) obj;

        boolean checkColor = color != null && f.color != null && color.equals(f.color);


        return checkColor;
    }

    @Override
    public String toString(){
        return name + " " + color;
    }
}
