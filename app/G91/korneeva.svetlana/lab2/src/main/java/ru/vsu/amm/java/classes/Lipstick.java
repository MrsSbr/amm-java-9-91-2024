package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.abstracts.AbstractCosmetic;
import ru.vsu.amm.java.enums.Brand;
import ru.vsu.amm.java.enums.Color;

import java.util.Objects;
public class Lipstick extends AbstractCosmetic {

    private Color color;

    public Lipstick(Brand brand, double price, Color color) {
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
        if (!super.equals(obj)) {
            return false;
        }
        Lipstick that = (Lipstick) obj;
        return getColor().equals(that.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }


}
