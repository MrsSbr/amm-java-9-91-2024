package ru.vsu.amm.java;

public class RAM extends HardwareComponent{
    private  int capacity;
    public RAM(String manufacturer, String model, double price, int capacity) {
        super(manufacturer, model, price);
        this.capacity=capacity;
    }

    @Override
    public String getDescription(){
        return "RAM with "+capacity+" GB capacity";
    }
}
