package ru.vsu.amm.java;

public class RAM extends HardwareComponent{
    private  final int capacity;
    public RAM(String manufacturer, String model, double price, int capacity) {
        super(manufacturer, model, price);
        this.capacity=capacity;
    }

    public String getDescription(){
        return "RAM with "+capacity+" GB capacity";
    }

    public int getCapacity(){return capacity;}
}
