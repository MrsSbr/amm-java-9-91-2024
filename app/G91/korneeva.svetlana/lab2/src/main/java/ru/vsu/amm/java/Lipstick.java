package ru.vsu.amm.java;

import java.util.Objects;
public class Lipstick extends AbstractCosmetic {

    private Color color;

    public Lipstick(String brand, double price, Color color) {
        super(brand, price);
        this.color=color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    @Override
    public String toString(){
        return super.toString() + " Color: " + color;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Lipstick that = (Lipstick) obj;
        return getColor().equals(that.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }


}
