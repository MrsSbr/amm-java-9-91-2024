package ru.vsu.amm.java;

import java.util.Objects;

public class Furniture extends Item implements FurnitureBuilder {
    private final int height;
    private final int width;
    private final int length;
    private final String color; //final doesn't work for strings, did this only to suppress warnings
    private final String type;
    private boolean isAssembled = false;


    public Furniture(String name,
                     int price,
                     int quantity,
                     int height,
                     int width,
                     int length,
                     String color,
                     String type) {
        super(name, price, quantity);
        this.height = height;
        this.width = width;
        this.length = length;
        this.color = color;
        this.type = type;
    }
    @Override
    public void disassemble() {
        isAssembled = false;
        System.out.println("Disassembled Furniture");
    }

    @Override
    public void build(){
        this.isAssembled = true;
        System.out.println("Furniture is build");
    }

    @Override
    public void perform() {
        if (this.type.equals("Sofa") || this.type.equals("Chair")) {
            System.out.println("Beautiful " + color +  " sofa\nI want to sit on it\n");
        } else if (this.type.equals("Table")) {
            System.out.println("Cool Table\n");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Furniture furniture = (Furniture) o;
        return Objects.equals(height, furniture.height)
                && Objects.equals(width, furniture.width)
                && Objects.equals(length, furniture.length)
                && Objects.equals(color, furniture.color)
                && Objects.equals(type, furniture.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height, width, length, color, type);
    }

    @Override
    public String toString() {
        return "Furniture:\n" +
                "Height=" + height + '\n' +
                "Width=" + width + '\n' +
                "Length=" + length + '\n' +
                "Color=" + color + '\n' +
                "Type=" + type + '\n' +
                "Is Assembled=" + isAssembled + '\n' +
                super.toString();
    }
}


