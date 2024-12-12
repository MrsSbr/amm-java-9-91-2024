package ru.vsu.amm.java;

public class GPU extends HardwareComponent{
    private final int memory;
    public GPU(String manufacturer, String model,double price, int memory) {
        super(manufacturer,model,price);
        this.memory=memory;
    }

    public  String getDescription(){
        return "GPU with "+memory+" GB of video memory";
    }

    public int getMemory(){return memory;}
}

