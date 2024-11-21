package ru.vsu.amm.java;

public class GPU extends HardwareComponent{
    private int memory;
    public GPU(String manufacturer, String model,double price, int memory) {
        super(manufacturer,model,price);
        this.memory=memory;
    }

    @Override
    public  String getDescription(){
        return "GPU with "+memory+" GB of video memory";
    }
}
